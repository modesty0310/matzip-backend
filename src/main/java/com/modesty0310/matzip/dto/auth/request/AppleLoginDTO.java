package com.modesty0310.matzip.dto.auth.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleLoginDTO {
    private String identityToken;
    private String appId;
    private String nickname;
}
