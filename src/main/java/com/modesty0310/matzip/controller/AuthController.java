package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.auth.request.EditProfileDTO;
import com.modesty0310.matzip.dto.auth.request.SigninRequestDTO;
import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.response.GetProfileDTO;
import com.modesty0310.matzip.dto.auth.response.RefreshTokenDTO;
import com.modesty0310.matzip.dto.auth.response.SigninResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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
        return authService.signIn(signinRequestDTO);
    }

    @GetMapping("/refresh")
    public RefreshTokenDTO getRefreshToken(HttpServletRequest request) {
        User userDetails = (User) request.getAttribute("user");
        return authService.refresh(userDetails);
    }

    @GetMapping("/me")
    public GetProfileDTO getProfile(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return new GetProfileDTO(user);
    }

    @PatchMapping("/me")
    public void editProfile(HttpServletRequest request, @Valid @RequestBody EditProfileDTO editProfileDTO) {
        User user = (User) request.getAttribute("user");
        authService.editProfile(editProfileDTO, user.getId());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        authService.logout(user.getId());
    }

    @DeleteMapping("/me")
    public void delete(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        authService.deleteAccount(user.getId());
    }
}
