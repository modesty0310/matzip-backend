package com.modesty0310.matzip.controller;

import com.modesty0310.matzip.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<List<String>> uploadImages(@RequestParam("images") MultipartFile[] images) {
        System.out.println(images);
        List<String> uris = imageService.saveFiles(images);
        return ResponseEntity.ok(uris);
    }
}
