package com.abdul.paylitebackend.email.emailSender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    private String recipient;
    private String subject;
    private String message;
}
