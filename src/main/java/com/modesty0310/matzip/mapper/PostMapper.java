package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.post.request.UpdatePostRequestDTO;
import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.dto.post.response.CreatePostResponseDTO;
import com.modesty0310.matzip.dto.post.response.PostWithFavoriteResultDTO;
import com.modesty0310.matzip.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    List<GetAllMarkersResponseDTO> getAllMarkers(Long userId);

    void createPost(Post post);

    Post getPosts(int limit, int offset, Long userId);

    Post getPostById(Long postId, Long userId);

    CreatePostResponseDTO getPostWithImagesById(Long postId);

    PostWithFavoriteResultDTO getPostWithFavoriteById(Long postId, Long userId);

    int deletePostById(Long postId, Long userId);

    void updatePost(@Param("updatePostRequestDTO") UpdatePostRequestDTO updatePostRequestDTO,
                    @Param("postId") Long postId);
}
