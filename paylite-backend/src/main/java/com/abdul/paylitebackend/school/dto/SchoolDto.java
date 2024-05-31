package com.abdul.paylitebackend.school.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SchoolDto {

    private String name;
    private String email;
    private String password;
    private Double balance;
}
