package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.post.response.GetAllMarkersResponseDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<GetAllMarkersResponseDTO> getAllMarkers(Long userId);
}
