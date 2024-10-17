package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.post.request.CreatePostRequestDTO;
import com.modesty0310.matzip.dto.post.response.CreatePostResponseDTO;
import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.dto.post.response.GetPostByIdResponseDTO;
import com.modesty0310.matzip.entity.Post;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public CreatePostResponseDTO createPost(@AuthenticationPrincipal User user, @Valid @RequestBody CreatePostRequestDTO createPostRequestDTO) {
        System.out.println(createPostRequestDTO.toString());
        return postService.createPost(createPostRequestDTO, user);
    }

    @GetMapping("/posts/my")
    public Post getPost(@RequestParam("page") int page, @AuthenticationPrincipal User user) {
        return postService.getPosts(page, user);
    }

    @GetMapping("/posts/{id}")
    public GetPostByIdResponseDTO getPostById(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        return postService.getPostById(id, user);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePostById(@AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        postService.deletePost(id, user);
    }
}
