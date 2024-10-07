package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.auth.request.SigninRequestDTO;
import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.response.SigninResponseDTO;
import com.modesty0310.matzip.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public void signUp(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        authService.signUp(signupRequestDTO);
    }

    @PostMapping("/signin")
    public SigninResponseDTO signIn(@Valid @RequestBody SigninRequestDTO signinRequestDTO) {
        return authService.signin(signinRequestDTO);
    }
}
