package com.modesty0310.matzip.service;

import com.modesty0310.matzip._enum.ErrorCode;
import com.modesty0310.matzip.dto.post.request.CreatePostRequestDTO;
import com.modesty0310.matzip.dto.post.request.SearchMyPostsByTitleAndAddressDTO;
import com.modesty0310.matzip.dto.post.request.UpdatePostRequestDTO;
import com.modesty0310.matzip.dto.post.response.*;
import com.modesty0310.matzip.entity.Image;
import com.modesty0310.matzip.entity.Post;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.exception.CustomException;
import com.modesty0310.matzip.mapper.ImageMapper;
import com.modesty0310.matzip.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public PostWithImageResultDTO createPost(CreatePostRequestDTO createPostDto, User user) {
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

    public PostWithFavoriteResultDTO getPostById(long postId, User user) {
        return postMapper.getPostWithFavoriteById(postId, user.getId());
    }

    public Long deletePost(long postId, User user) {
        int deletedRows = postMapper.deletePostById(postId, user.getId());
        if (deletedRows < 1) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }
        return postId;
    }

    public PostWithFavoriteResultDTO updatePost(long postId, UpdatePostRequestDTO updatePostDto, User user) {
        Post post = postMapper.getPostById(postId, user.getId());
        if (post == null) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        postMapper.updatePost(updatePostDto, postId);

        // 기존 이미지 지우기
        imageMapper.deleteImageByPostId(postId);

        // Now map imageUris to Image entities
        List<Image> images = updatePostDto.getImageUris().stream()
                .map(uriDTO -> new Image(uriDTO.getUri(), post))
                .collect(Collectors.toList());

        // Save the images
        for (Image image : images) {
            imageMapper.createImage(image);
        }

        return postMapper.getPostWithFavoriteById(post.getId(), user.getId());
    }

    public Map<Integer, List<GetPostByMonthDTO>> getPostByMonth(int year, int month, User user) {
        List<GetPostByMonthDTO> posts = postMapper.getPostByMonth(year, month, user.getId());
        System.out.println("Posts: " + posts);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
        System.out.println("UserId: " + user.getId());
        // 날짜(day)를 기준으로 데이터를 그룹화
        return posts.stream()
                .collect(Collectors.groupingBy(GetPostByMonthDTO::getDay));
    }

    public List<PostWithImageResultDTO> searchMyPostsByTitleAndAddress(SearchMyPostsByTitleAndAddressDTO searchMyPostsByTitleAndAddressDTO) {
        return postMapper.searchMyPostsByTitleAndAddress(searchMyPostsByTitleAndAddressDTO);
    }
}
