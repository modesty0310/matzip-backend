package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.dto.post.response.CreatePostResponseDTO;
import com.modesty0310.matzip.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<GetAllMarkersResponseDTO> getAllMarkers(Long userId);

    void createPost(Post post);

    Post getPosts(int limit, int offset, Long userId);

    CreatePostResponseDTO getPostWithImagesById(Long postId);
}
