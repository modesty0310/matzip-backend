package com.modesty0310.matzip.service;

import com.modesty0310.matzip.config.JwtTokenProvider;
import com.modesty0310.matzip.dto.auth.request.SigninRequestDTO;
import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.request.UpdateHashedRefreshTokenDTO;
import com.modesty0310.matzip.dto.auth.response.SigninResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
            return;
        }

        signupRequestDTO.setPassword(bCryptPasswordEncoder.encode(password));
        signupRequestDTO.setLoginType("email");

        authMapper.signUp(signupRequestDTO);
    }

    public SigninResponseDTO signin(SigninRequestDTO signinRequestDTO) {

        String email = signinRequestDTO.getEmail();
        String password = signinRequestDTO.getPassword();

        // DB에서 사용자 조회
        User user = authMapper.getUserByEmail(email);

        // 비밀번호 확인
        if (user == null || !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateToken(email, true);
        String refreshToken = jwtTokenProvider.generateToken(email, false);

        // refreshToken을 DB에 저장하거나 업데이트하는 로직 추가 필요
        String hashedRefreshToken = bCryptPasswordEncoder.encode(refreshToken);
        UpdateHashedRefreshTokenDTO updateHashedRefreshTokenDTO = UpdateHashedRefreshTokenDTO
                .builder()
                .userId(user.getId())
                .hashedRefreshToken(hashedRefreshToken)
                .build();
        authMapper.updateHashedRefreshToken(updateHashedRefreshTokenDTO);

        return SigninResponseDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
