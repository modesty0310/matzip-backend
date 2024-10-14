package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.auth.request.EditProfileDTO;
import com.modesty0310.matzip.dto.auth.request.SigninRequestDTO;
import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.request.UpdateCategoryRequestDTO;
import com.modesty0310.matzip.dto.auth.response.GetProfileDTO;
import com.modesty0310.matzip.dto.auth.response.RefreshTokenDTO;
import com.modesty0310.matzip.dto.auth.response.SigninResponseDTO;
import com.modesty0310.matzip.dto.auth.response.UpdateCategoryResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public RefreshTokenDTO getRefreshToken(@AuthenticationPrincipal User user) {
        return authService.refresh(user);
    }

    @GetMapping("/me")
    public GetProfileDTO getProfile(@AuthenticationPrincipal User user) {
        return new GetProfileDTO(user);
    }

    @PatchMapping("/me")
    public void editProfile(@AuthenticationPrincipal User user, @Valid @RequestBody EditProfileDTO editProfileDTO) {
        authService.editProfile(editProfileDTO, user.getId());
    }

    @PostMapping("/logout")
    public void logout(@AuthenticationPrincipal User user) {
        authService.logout(user.getId());
    }

    @DeleteMapping("/me")
    public void delete(@AuthenticationPrincipal User user) {
        authService.deleteAccount(user.getId());
    }

    @PatchMapping("/category")
    public UpdateCategoryResponseDTO updateCategory(
            @Valid @RequestBody UpdateCategoryRequestDTO updateCategoryDTO,
            @AuthenticationPrincipal User user) {
        return authService.updateCategory(updateCategoryDTO.getCategories(), user);
    }
}
