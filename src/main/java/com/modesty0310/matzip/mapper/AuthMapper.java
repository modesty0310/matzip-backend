package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.request.UpdateHashedRefreshTokenDTO;
import com.modesty0310.matzip.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

    void signUp(SignupRequestDTO signupRequestDTO);

    boolean existsByEmail(String email);

    User getUserByEmail(String email);

    void updateHashedRefreshToken(UpdateHashedRefreshTokenDTO updateHashedRefreshTokenDTO);
}
