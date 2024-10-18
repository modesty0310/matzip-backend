package com.modesty0310.matzip.dto.auth.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class KakaoSignupDTO {
    private String email;
    private String password;
    private String nickname;
    private String kakaoImageUri;
    private String loginType;
}
