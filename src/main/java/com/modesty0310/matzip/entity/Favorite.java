package com.modesty0310.matzip.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    private Long id;
    private Long postId;
    private Long userId;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Post post;
    private User user;
}

