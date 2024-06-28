package com.abdul.paylitebackend.jwt;

import com.abdul.paylitebackend.school.dto.LoginRequest;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.repository.SchoolRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Data
@Service
@RequiredArgsConstructor
public class JwtAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SchoolRepository schoolRepository;

    public JwtAuthResponseDto login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        var user = schoolRepository.findByEmail(loginRequest.getUsername()).orElseThrow(() -> new IllegalStateException("Invalid EMail"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.refreshToken(new HashMap<>(), user);

        JwtAuthResponseDto jwtAuthResponseDto = new JwtAuthResponseDto();

        jwtAuthResponseDto.setSchoolId(user.getId());
        jwtAuthResponseDto.setSchoolName(user.getName());
        jwtAuthResponseDto.setAccessToken(jwt);
        jwtAuthResponseDto.setRefreshToken(refreshToken);

        return jwtAuthResponseDto;
    }
}
