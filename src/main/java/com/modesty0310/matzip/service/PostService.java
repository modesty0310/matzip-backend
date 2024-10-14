package com.modesty0310.matzip.service;

import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public List<GetAllMarkersResponseDTO> getAllMarkers(User user) {
        // MyBatis 매퍼 호출하여 마커 리스트 가져오기
        List<GetAllMarkersResponseDTO> markers = postMapper.getAllMarkers(user.getId());

        // 결과를 출력
        System.out.println("Markers: " + markers); // markers 리스트 출력
        return markers;
    }
}
