package com.modesty0310.matzip.dto.auth.response;

import com.modesty0310.matzip.entity.Favorite;
import com.modesty0310.matzip.entity.Post;
import com.modesty0310.matzip.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UpdateCategoryResponseDTO {
    private Long id;
    private String loginType;
    private String email;
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
    private List<Post> post;
    private List<Favorite> favorites;

    public UpdateCategoryResponseDTO(User user) {
        this.id = user.getId();
        this.loginType = user.getLoginType();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.imageUri = user.getImageUri();
        this.kakaoImageUri = user.getKakaoImageUri();
        this.red = user.getRed();
        this.yellow = user.getYellow();
        this.green = user.getGreen();
        this.blue = user.getBlue();
        this.purple = user.getPurple();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.deletedAt = user.getDeletedAt();
        this.post = user.getPost();
        this.favorites = user.getFavorites();
    }
}
