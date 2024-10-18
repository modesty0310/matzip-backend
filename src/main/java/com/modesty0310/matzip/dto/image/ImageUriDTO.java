package com.modesty0310.matzip.dto.image;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageUriDTO {
    @NotEmpty(message = "URI is required")
    private String uri;
}
