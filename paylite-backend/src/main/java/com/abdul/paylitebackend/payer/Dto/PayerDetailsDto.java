package com.abdul.paylitebackend.payer.Dto;

import lombok.Data;

@Data
public class PayerDetailsDto {

    private String name;
    private String email;
    private String phoneNumber;
    private Integer amount;
    private String service;
}
