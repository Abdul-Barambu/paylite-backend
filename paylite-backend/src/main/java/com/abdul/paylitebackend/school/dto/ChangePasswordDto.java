package com.abdul.paylitebackend.school.dto;

import lombok.*;

@Data
@Getter
public class ChangePasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
