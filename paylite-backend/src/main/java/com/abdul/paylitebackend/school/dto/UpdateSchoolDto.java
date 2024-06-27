package com.abdul.paylitebackend.school.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UpdateSchoolDto {
    private String address;
    private String phoneNumber;
    private String state;
    private String bankName;
    private String accountName;
    private String accountNumber;
    private String logo;
    private String documents;
}
