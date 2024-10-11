package com.modesty0310.matzip.dto.auth.response;

import lombok.Builder;

@Builder
public class RefreshTokenDTO {
    private String accessToken;
    private String refreshToken;
}
