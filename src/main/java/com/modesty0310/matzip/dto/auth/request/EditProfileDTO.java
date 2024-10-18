package com.modesty0310.matzip.dto.auth.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditProfileDTO {

    @Size(min = 1, max = 20, message = "닉네임의 글자수는 1 ~ 20 이여야 합니다.")
    @NotBlank(message = "닉네임은 필수 입니다.")
    private String nickname;

    @NotBlank(message = "이미지는 필수 입니다.")
    private String imageUri;
}
