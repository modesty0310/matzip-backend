package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.auth.SignupDTO;
import com.modesty0310.matzip.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public void signUp(@RequestBody SignupDTO signupDTO) {
        authService.signUp(signupDTO);
    }
}
