package com.modesty0310.matzip.mapper;

import com.modesty0310.matzip.dto.auth.request.EditProfileDTO;
import com.modesty0310.matzip.dto.auth.request.SignupRequestDTO;
import com.modesty0310.matzip.dto.auth.request.UpdateHashedRefreshTokenDTO;
import com.modesty0310.matzip.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {

    void signUp(SignupRequestDTO signupRequestDTO);

    boolean existsByEmail(String email);

    User getUserByEmail(String email);

    User getUserById(Long id);

    void updateHashedRefreshToken(UpdateHashedRefreshTokenDTO updateHashedRefreshTokenDTO);

    void deleteHashedRefreshToken(Long userId);

    void editProfile(@Param("editProfileDTO") EditProfileDTO editProfileDTO, @Param("userId") Long userId);
}
