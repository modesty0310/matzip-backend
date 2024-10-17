package com.modesty0310.matzip.dto.post.response;

import com.modesty0310.matzip._enum.MarkerColor;
import com.modesty0310.matzip.dto.image.ImageDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GetPostByIdResponseDTO {
    private Long id;

    private Double latitude;

    private Double longitude;

    private MarkerColor color;

    private String address;

    private String title;

    private String description;

    private Date date; // or LocalDateTime for better handling

    private Integer score;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private List<ImageDTO> images;

    private boolean isFavorite;
}
