package com.abdul.paylitebackend.school.dto;

import lombok.Data;

@Data
public class ForgetPasswordDto {
    private Long id;
    private String otp;
    private String newPassword;
    private String confirmPassword;
}
