package com.modesty0310.matzip.dto.auth.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateHashedRefreshTokenDTO {
    private String hashedRefreshToken;
    private Long userId;
}
