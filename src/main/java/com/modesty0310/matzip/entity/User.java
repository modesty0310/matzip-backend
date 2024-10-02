package com.modesty0310.matzip.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String loginType;
    private String email;
    private String password;
    private String nickname;
    private String imageUri;
    private String kakaoImageUri;
    private String red;
    private String yellow;
    private String green;
    private String blue;
    private String purple;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private String hashedRefreshToken;
    private List<Post> post;
    private List<Favorite> favorites;
}

