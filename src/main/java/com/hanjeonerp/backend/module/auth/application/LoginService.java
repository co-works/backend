package com.hanjeonerp.backend.module.auth.application;

import com.hanjeonerp.backend.core.exception.BadRequestException;
import com.hanjeonerp.backend.core.exception.ErrorCode;
import com.hanjeonerp.backend.module.auth.api.dto.LoginReq;
import com.hanjeonerp.backend.module.auth.api.dto.LoginRes;
import com.hanjeonerp.backend.module.auth.jwt.JwtTokenProvider;
import com.hanjeonerp.backend.module.user.domain.entity.User;
import com.hanjeonerp.backend.module.user.domain.repo.UserRepo;
import com.hanjeonerp.backend.module.user.domain.vo.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 로그인 메서드
    public LoginRes login(LoginReq dto) {

        Username username = new Username(dto.getUsername());

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new BadRequestException(ErrorCode.UNAUTHORIZED, "존재하지 않는 사용자입니다."));

        System.out.println(passwordEncoder.encode("admin1234"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorCode.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }



        // 토큰 발급
        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );;
        // 로그인 성공 시 응답 DTO 생성
        return LoginRes.from(user, accessToken);

    }

}