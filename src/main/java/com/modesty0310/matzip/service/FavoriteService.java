package com.modesty0310.matzip.service;

import com.modesty0310.matzip.dto.favorite.request.GetPostByMyFavoriteDTO;
import com.modesty0310.matzip.dto.favorite.request.ToggleFavoriteDTO;
import com.modesty0310.matzip.dto.favorite.response.PostWithImageResultDTO;
import com.modesty0310.matzip.entity.Favorite;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.mapper.FavoriteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    public List<PostWithImageResultDTO> getPostByMyFavorite(int page, User user) {
        GetPostByMyFavoriteDTO dto = GetPostByMyFavoriteDTO
                .builder()
                .limit(10)
                .offset((page - 1) * 10)
                .userId(user.getId())
                .build();
        return favoriteMapper.getPostByMyFavorite(dto);
    }

    public long toggleFavorite(long postId, User user) {
        ToggleFavoriteDTO dto = ToggleFavoriteDTO
                .builder()
                .postId(postId)
                .userId(user.getId())
                .build();

        Favorite existFavorite = favoriteMapper.getFavoriteByPostIdAndUserId(dto);
        if (existFavorite != null) {
            favoriteMapper.deleteFavoriteByPostIdAndUserId(dto);
            return postId;
        }

        favoriteMapper.createFavorite(dto);
        return postId;
    }
}
