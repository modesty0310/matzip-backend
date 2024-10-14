package com.modesty0310.matzip.service;

import com.modesty0310.matzip._enum.ErrorCode;
import com.modesty0310.matzip._enum.MarkerColor;
import com.modesty0310.matzip.config.JwtTokenProvider;
import com.modesty0310.matzip.dto.auth.request.*;
import com.modesty0310.matzip.dto.auth.response.RefreshTokenDTO;
import com.modesty0310.matzip.dto.auth.response.SigninResponseDTO;
import com.modesty0310.matzip.dto.auth.response.UpdateCategoryResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.exception.CustomException;
import com.modesty0310.matzip.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RestTemplate restTemplate; // HTTP 요청을 위한 RestTemplate

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

    public void deleteAccount(Long userId) {
        authMapper.deleteAccount(userId);
    }

    public UpdateCategoryResponseDTO updateCategory(Map<MarkerColor, String> categories, User user) {
        // Enum 값을 추출하여 사용
        MarkerColor[] validColors = MarkerColor.values();

        // 유효성 검사: 주어진 카테고리가 MarkerColor 배열에 존재하는지 확인
        boolean isValid = categories.keySet().stream()
                .allMatch(color -> {
                    for (MarkerColor validColor : validColors) {
                        if (validColor.equals(color)) {
                            return true;
                        }
                    }
                    return false;
                });

        if (!isValid) {
            throw new IllegalArgumentException("유효하지 않은 카테고리입니다.");
        }

        // 사용자 객체에 카테고리 값 설정
        user.setRed(categories.get(MarkerColor.RED));
        user.setYellow(categories.get(MarkerColor.YELLOW));
        user.setBlue(categories.get(MarkerColor.BLUE));
        user.setGreen(categories.get(MarkerColor.GREEN));
        user.setPurple(categories.get(MarkerColor.PURPLE));

        try {
            // 변경 사항을 저장
            authMapper.updateCategory(user);
        } catch (Exception e) {
            throw new RuntimeException("카테고리 수정 도중 에러가 발생했습니다.");
        }

        // 비밀번호, 리프레시 토큰 제외 후 반환
        return new UpdateCategoryResponseDTO(user);
    }

    public SigninResponseDTO kakaoLogin(String kakaoToken) {
        String url = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + kakaoToken);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, Object> userData = response.getBody();
            String kakaoId = (String) userData.get("id");
            Map<String, Object> kakaoAccount = (Map<String, Object>) userData.get("kakao_account");
            String nickname = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
            String imageUri = ((String) ((Map<String, Object>) kakaoAccount.get("profile")).get("thumbnail_image_url")).replace("http:", "https:");

            // User가 이미 존재하는지 확인
            User existingUser = authMapper.getUserByEmail(kakaoId);
            if (existingUser != null) {
                // JWT 토큰 생성
                String accessToken = jwtTokenProvider.generateToken(existingUser.getEmail(), true);
                String refreshToken = jwtTokenProvider.generateToken(existingUser.getEmail(), false);

                updateRefreshToken(refreshToken, existingUser.getId());

                return SigninResponseDTO
                        .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }

            KakaoSignupDTO kakaoSignupDTO = KakaoSignupDTO
                    .builder()
                    .email(kakaoId)
                    .password(nickname != null ? nickname : "")
                    .nickname(nickname)
                    .kakaoImageUri(imageUri)
                    .loginType("kakao")
                    .build();

            try {
                authMapper.kakaoSignUp(kakaoSignupDTO);
            } catch (Exception e) {
                throw new CustomException(ErrorCode.FAILED_KAKAO_SIGNUP);
            }

            // JWT 토큰 생성
            String accessToken = jwtTokenProvider.generateToken(existingUser.getEmail(), true);
            String refreshToken = jwtTokenProvider.generateToken(existingUser.getEmail(), false);

            updateRefreshToken(refreshToken, existingUser.getId());

            return SigninResponseDTO
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new CustomException(ErrorCode.KAKAO_SERVER_ERROR);
        }
    }
}
