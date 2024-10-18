package com.modesty0310.matzip.dto.post.request;

import com.modesty0310.matzip._enum.MarkerColor;
import com.modesty0310.matzip.dto.image.ImageUriDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class CreatePostRequestDTO {
    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotNull(message = "Color is required")
    private MarkerColor color;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "Title is required")
    private String title;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Date is required")
    private Date date; // or LocalDateTime for better handling

    @NotNull(message = "Score is required")
    private Integer score;

    @NotNull(message = "Image URIs are required")
    private List<ImageUriDTO> imageUris;
}
