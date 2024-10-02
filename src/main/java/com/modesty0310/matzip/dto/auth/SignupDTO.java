package com.modesty0310.matzip.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    String email;
    String password;
    String loginType;
}
