package com.abdul.paylitebackend.jwt;

import lombok.Data;

@Data
public class JwtAuthResponseDto {
    private String jwtToken;
    private String refreshToken;
}
