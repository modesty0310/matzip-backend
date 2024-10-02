package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.auth.SignupDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    void signUp(SignupDTO signupDTO);

    boolean existsByEmail(String email);
}
