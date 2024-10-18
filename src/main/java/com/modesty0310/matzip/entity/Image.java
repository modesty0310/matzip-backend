package com.modesty0310.matzip.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    private Long id;
    private String uri;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private Post post;

    public Image(String uri, Post post) {
        this.uri = uri;
        this.post = post;
    }
}
