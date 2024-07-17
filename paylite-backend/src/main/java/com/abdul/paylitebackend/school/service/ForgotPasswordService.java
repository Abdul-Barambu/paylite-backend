package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.email.emailSender.EmailDto;
import com.abdul.paylitebackend.email.emailSender.EmailService;
import com.abdul.paylitebackend.school.dto.ForgotPasswordEmailDto;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private static final String NUMERIC_STRING = "0123456789";
    private static final int REFERENCE_NUMBER_LENGTH = 5;

    private final EmailService emailService;
    private final SchoolRepository schoolRepository;

    public String generateOtpNumber(ForgotPasswordEmailDto forgotPasswordEmailDto) {
        StringBuilder builder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < REFERENCE_NUMBER_LENGTH; i++) {
            int character = random.nextInt(NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }

//        validate email
        Optional<Schools> optionalSchools = schoolRepository.findByEmail(forgotPasswordEmailDto.getEmail());

        if (optionalSchools.isEmpty()){
            throw new IllegalStateException("Email not found");
        }

        EmailDto emailDto = EmailDto.builder()
                .recipient(forgotPasswordEmailDto.getEmail())
                .subject("FORGOT PASSWORD OTP")
                .message("Your requested OTP for forgot password is "+builder.toString())
                .build();
        emailService.sendEmailAlert(emailDto);
        return builder.toString();
    }


}
