package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
