package com.modesty0310.matzip.dto.auth.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SigninResponseDTO {
    private String accessToken;
    private String refreshToken;
}
