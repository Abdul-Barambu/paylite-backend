package com.abdul.paylitebackend.school.controller;

import com.abdul.paylitebackend.jwt.JwtAuthResponseDto;
import com.abdul.paylitebackend.jwt.JwtAuthService;
import com.abdul.paylitebackend.school.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final JwtAuthService jwtAuthService;

    @PostMapping(path = "/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(jwtAuthService.login(loginRequest));
    }
}
