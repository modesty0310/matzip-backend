package com.modesty0310.matzip.dto.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RefreshTokenDTO {
    private String accessToken;
    private String refreshToken;
}
