package com.modesty0310.matzip.dto.post.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetPostByMonthDTO {
    private Long id;
    private String title;
    private String address;
    private int day; // 날짜의 "일" 정보를 담음
}
