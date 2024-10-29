package com.modesty0310.matzip.dto.favorite.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetPostByMyFavoriteDTO {
    private long userId;
    private int limit;
    private int offset;
}
