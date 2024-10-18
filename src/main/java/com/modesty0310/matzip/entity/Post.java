package com.modesty0310.matzip.entity;

import com.modesty0310.matzip._enum.MarkerColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Long id;
    private Double latitude;
    private Double longitude;
    private MarkerColor color;
    private String address;
    private String title;
    private String description;
    private Date date;
    private Integer score;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private User user;
    private List<Image> images;
    private List<Favorite> favorites;
}

