package com.modesty0310.matzip.dto.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchMyPostsByTitleAndAddressDTO {
    private String search;
    private Long userId;
    private int limit;
    private int offset;
}
