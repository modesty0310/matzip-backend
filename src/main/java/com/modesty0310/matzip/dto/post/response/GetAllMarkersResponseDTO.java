package com.modesty0310.matzip.dto.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetAllMarkersResponseDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String color;
    private Integer score;
}
