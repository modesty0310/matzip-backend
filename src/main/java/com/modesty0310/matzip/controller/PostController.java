package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.post.request.CreatePostRequestDTO;
import com.modesty0310.matzip.dto.post.request.SearchMyPostsByTitleAndAddressDTO;
import com.modesty0310.matzip.dto.post.request.UpdatePostRequestDTO;
import com.modesty0310.matzip.dto.post.response.PostWithImageResultDTO;
import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.dto.post.response.GetPostByMonthDTO;
import com.modesty0310.matzip.dto.post.response.PostWithFavoriteResultDTO;
import com.modesty0310.matzip.entity.Post;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/markers/my")
    public List<GetAllMarkersResponseDTO> getAllMarkers(@AuthenticationPrincipal User user) {
        System.out.println(user.toString());
        return postService.getAllMarkers(user);
    }

    @PostMapping("/posts")
    public PostWithImageResultDTO createPost(@AuthenticationPrincipal User user, @Valid @RequestBody CreatePostRequestDTO createPostRequestDTO) {
        System.out.println(createPostRequestDTO.toString());
        return postService.createPost(createPostRequestDTO, user);
    }

    @GetMapping("/posts/my")
    public Post getPost(@RequestParam("page") int page, @AuthenticationPrincipal User user) {
        return postService.getPosts(page, user);
    }

    @GetMapping("/posts/{id}")
    public PostWithFavoriteResultDTO getPostById(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return postService.getPostById(id, user);
    }

    @DeleteMapping("/posts/{id}")
    public Long deletePostById(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return postService.deletePost(id, user);
    }

    @PatchMapping("/posts/{id}")
    public PostWithFavoriteResultDTO updatePost(
            @AuthenticationPrincipal User user,
            @PathVariable("id") Long id,
            @RequestBody UpdatePostRequestDTO updatePostRequestDTO) {
        return postService.updatePost(id, updatePostRequestDTO, user);
    }

    @GetMapping("/posts")
    public ResponseEntity<Map<Integer, List<GetPostByMonthDTO>>> getPostsByMonth(
            @RequestParam("year") int year,
            @RequestParam("month") int month,
            @AuthenticationPrincipal User user
    ) {
        Map<Integer, List<GetPostByMonthDTO>> postsGroupedByDay = postService.getPostByMonth(year, month, user);
        return ResponseEntity.ok(postsGroupedByDay);
    }

    @GetMapping("/posts/my/search")
    public List<PostWithImageResultDTO> searchMyPostsByTitleAndAddress(
            @RequestParam("query") String search,
            @RequestParam("page") int page,
            @AuthenticationPrincipal User user
    ) {
        int limit = 10;
        SearchMyPostsByTitleAndAddressDTO dto = SearchMyPostsByTitleAndAddressDTO
                .builder()
                .limit(limit)
                .offset((page - 1) * limit)
                .search(search)
                .userId(user.getId())
                .build();

        return postService.searchMyPostsByTitleAndAddress(dto);
    }
}
