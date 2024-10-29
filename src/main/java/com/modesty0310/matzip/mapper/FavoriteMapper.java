package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.favorite.request.GetPostByMyFavoriteDTO;
import com.modesty0310.matzip.dto.favorite.request.ToggleFavoriteDTO;
import com.modesty0310.matzip.dto.favorite.response.PostWithImageResultDTO;
import com.modesty0310.matzip.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FavoriteMapper {

    List<PostWithImageResultDTO> getPostByMyFavorite(GetPostByMyFavoriteDTO postByMyFavoriteDTO);

    void createFavorite(ToggleFavoriteDTO toggleFavoriteDTO);

    Favorite getFavoriteByPostIdAndUserId(ToggleFavoriteDTO toggleFavoriteDTO);

    void deleteFavoriteByPostIdAndUserId(ToggleFavoriteDTO toggleFavoriteDTO);
}
