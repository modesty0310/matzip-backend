package com.modesty0310.matzip.service;

import com.modesty0310.matzip.dto.auth.SignupDTO;
import com.modesty0310.matzip.mapper.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(SignupDTO signupDTO) {

        String email = signupDTO.getEmail();
        String password = signupDTO.getPassword();

        Boolean isExists = authMapper.existsByEmail(email);

        if (isExists) {
            return;
        }

        signupDTO.setPassword(bCryptPasswordEncoder.encode(password));
        signupDTO.setLoginType("email");

        authMapper.signUp(signupDTO);
    }
}
