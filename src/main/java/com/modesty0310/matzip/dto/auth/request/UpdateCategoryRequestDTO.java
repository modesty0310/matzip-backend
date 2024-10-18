package com.modesty0310.matzip.dto.auth.request;

import com.modesty0310.matzip._enum.MarkerColor;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UpdateCategoryRequestDTO {
    @NotNull(message = "카테고리를 입력해 주세요.")
    private Map<MarkerColor, String> categories;
}

