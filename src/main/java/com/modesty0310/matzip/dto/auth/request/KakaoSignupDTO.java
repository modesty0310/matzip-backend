package com.modesty0310.matzip.dto.auth.request;

import lombok.Builder;

@Builder
public class KakaoSignupDTO {
    private String email;
    private String password;
    private String nickname;
    private String kakaoImageUri;
    private String loginType;
}
