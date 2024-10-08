package com.modesty0310.matzip.service;

import com.modesty0310.matzip.entity.User;
import com.modesty0310.matzip.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }
}
