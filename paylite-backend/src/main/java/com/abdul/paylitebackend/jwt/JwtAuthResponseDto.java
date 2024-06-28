package com.abdul.paylitebackend.jwt;

import lombok.Data;

@Data
public class JwtAuthResponseDto {
    private Long schoolId;
    private String schoolName;
    private String accessToken;
    private String refreshToken;
}
