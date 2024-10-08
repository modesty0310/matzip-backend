package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getUserByEmail(String email);
}
