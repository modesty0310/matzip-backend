package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.auth.SignupDTO;
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
    public void signUp(@Valid @RequestBody SignupDTO signupDTO) {
        authService.signUp(signupDTO);
    }
}
