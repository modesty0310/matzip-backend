package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.dto.favorite.response.PostWithImageResultDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/my")
    public List<PostWithImageResultDTO> getPostByMyFavorite(
            @RequestParam("page") int page,
            @AuthenticationPrincipal User user
    ) {
        return favoriteService.getPostByMyFavorite(page, user);
    }

    @PostMapping("{id}")
    public long toggleFavorite(
            @PathVariable("id") long id,
            @AuthenticationPrincipal User user
    ) {
        return favoriteService.toggleFavorite(id, user);
    }
}
