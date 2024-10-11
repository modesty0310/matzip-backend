package com.modesty0310.matzip.service;

import com.modesty0310.matzip._enum.ErrorCode;
import com.modesty0310.matzip.config.JwtTokenProvider;
import com.modesty0310.matzip.dto.auth.request.EditProfileDTO;
import com.modesty0310.matzip.dto.auth.request.SigninRequestDTO;
import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.request.UpdateHashedRefreshTokenDTO;
import com.modesty0310.matzip.dto.auth.response.RefreshTokenDTO;
import com.modesty0310.matzip.dto.auth.response.SigninResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.exception.CustomException;
import com.modesty0310.matzip.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void signUp(SignupRequestDTO signupRequestDTO) {

        String email = signupRequestDTO.getEmail();
        String password = signupRequestDTO.getPassword();

        Boolean isExists = authMapper.existsByEmail(email);

        if (isExists) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        signupRequestDTO.setPassword(bCryptPasswordEncoder.encode(password));
        signupRequestDTO.setLoginType("email");

        authMapper.signUp(signupRequestDTO);
    }

    // refreshToken을 DB에 저장하거나 업데이트하는 로직
    private void updateRefreshToken(String refreshToken, Long userId) {
        String hashedRefreshToken = bCryptPasswordEncoder.encode(refreshToken);
        UpdateHashedRefreshTokenDTO updateHashedRefreshTokenDTO = UpdateHashedRefreshTokenDTO
                .builder()
                .userId(userId)
                .hashedRefreshToken(hashedRefreshToken)
                .build();
        authMapper.updateHashedRefreshToken(updateHashedRefreshTokenDTO);
    }

    public SigninResponseDTO signIn(SigninRequestDTO signinRequestDTO) {

        String email = signinRequestDTO.getEmail();
        String password = signinRequestDTO.getPassword();

        // DB에서 사용자 조회
        User user = authMapper.getUserByEmail(email);

        // 비밀번호 확인
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_EMAIL_OR_PASSWORD);
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateToken(email, true);
        String refreshToken = jwtTokenProvider.generateToken(email, false);

        updateRefreshToken(refreshToken, user.getId());

        return SigninResponseDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public RefreshTokenDTO refresh(User user) {
        String accessToken = jwtTokenProvider.generateToken(user.getEmail(), true);
        String refreshToken = jwtTokenProvider.generateToken(user.getEmail(), false);

        if(user.getHashedRefreshToken() == null) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        updateRefreshToken(refreshToken, user.getId());

        return RefreshTokenDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(Long userId) {
        authMapper.deleteHashedRefreshToken(userId);
    }

    public void editProfile(EditProfileDTO editProfileDTO, Long userId) {
        User user = authMapper.getUserById(userId);

        if (user == null) {
            throw new CustomException(ErrorCode.NOTFOUND_USER);
        }

        authMapper.editProfile(editProfileDTO, userId);
    }
}
