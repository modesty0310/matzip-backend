package com.modesty0310.matzip.dto.favorite.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ToggleFavoriteDTO {
    private long userId;
    private long postId;
}
