package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    void createImage(Image image);
}
