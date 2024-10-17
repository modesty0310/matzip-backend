package com.modesty0310.matzip.service;

import com.modesty0310.matzip.dto.post.request.CreatePostRequestDTO;
import com.modesty0310.matzip.dto.post.response.CreatePostResponseDTO;
import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.dto.post.response.GetPostByIdResponseDTO;
import com.modesty0310.matzip.entity.Image;
import com.modesty0310.matzip.entity.Post;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.mapper.ImageMapper;
import com.modesty0310.matzip.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;
    private final ImageMapper imageMapper;

    public List<GetAllMarkersResponseDTO> getAllMarkers(User user) {
        // MyBatis 매퍼 호출하여 마커 리스트 가져오기
        List<GetAllMarkersResponseDTO> markers = postMapper.getAllMarkers(user.getId());

        // 결과를 출력
        System.out.println("Markers: " + markers); // markers 리스트 출력
        return markers;
    }

    public CreatePostResponseDTO createPost(CreatePostRequestDTO createPostDto, User user) {
        Post post = new Post();
        post.setLatitude(createPostDto.getLatitude());
        post.setLongitude(createPostDto.getLongitude());
        post.setColor(createPostDto.getColor());
        post.setAddress(createPostDto.getAddress());
        post.setTitle(createPostDto.getTitle());
        post.setDescription(createPostDto.getDescription());
        post.setDate(createPostDto.getDate());
        post.setScore(createPostDto.getScore());
        post.setUser(user);

        postMapper.createPost(post);

        // Now map imageUris to Image entities
        List<Image> images = createPostDto.getImageUris().stream()
                .map(uriDTO -> new Image(uriDTO.getUri(), post))
                .collect(Collectors.toList());

        // Save the images
        for (Image image : images) {
            imageMapper.createImage(image);
        }

        return postMapper.getPostWithImagesById(post.getId());
    }

    public Post getPosts(int page, User user) {
        int limit = 10;
        int offset = (page - 1) * limit;
        return postMapper.getPosts(limit, offset, user.getId());
    }

    public GetPostByIdResponseDTO getPostById(long postId, User user) {
        return postMapper.getPostWithFavoriteById(postId, user.getId());
    }
}
