package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.email.emailSender.EmailDto;
import com.abdul.paylitebackend.email.emailSender.EmailService;
import com.abdul.paylitebackend.school.dto.ForgetPasswordDto;
import com.abdul.paylitebackend.school.dto.ForgotPasswordEmailDto;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private static final String NUMERIC_STRING = "0123456789";
    private static final int REFERENCE_NUMBER_LENGTH = 5;
    private static final ConcurrentHashMap<String, String> otpStored = new ConcurrentHashMap<>();

    private final EmailService emailService;
    private final SchoolRepository schoolRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String generateOtpNumber(ForgotPasswordEmailDto forgotPasswordEmailDto) {
        StringBuilder builder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < REFERENCE_NUMBER_LENGTH; i++) {
            int character = random.nextInt(NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }

//        validate email
        Optional<Schools> optionalSchools = schoolRepository.findByEmail(forgotPasswordEmailDto.getEmail());

        if (optionalSchools.isEmpty()) {
            throw new IllegalStateException("Email not found");
        }

        otpStored.put(forgotPasswordEmailDto.getEmail(), builder.toString());

        EmailDto emailDto = EmailDto.builder()
                .recipient(forgotPasswordEmailDto.getEmail())
                .subject("FORGOT PASSWORD OTP")
                .message("Your requested OTP for forgot password is " + builder.toString())
                .build();
        emailService.sendEmailAlert(emailDto);
        return builder.toString();
    }

    public String forgotPassword(Long id, ForgetPasswordDto forgetPasswordDto) {
        Optional<Schools> schools = schoolRepository.findById(id);

        if (schools.isEmpty()) {
            throw new IllegalStateException("ID not found");
        }

        Schools school = schools.get();
        String email = school.getEmail();

        String storedOtp = otpStored.get(email);

        if(storedOtp == null || !storedOtp.equals(forgetPasswordDto.getOtp())){
            throw new IllegalStateException("Invalid Otp");
        }

        if(!forgetPasswordDto.getNewPassword().equals(forgetPasswordDto.getConfirmPassword())){
            throw new IllegalStateException("Password and Confirm password do not match");
        }

        school.setPassword(bCryptPasswordEncoder.encode(forgetPasswordDto.getNewPassword()));
        schoolRepository.save(school);

        otpStored.remove(email);

        return "Password changed successfully";
    }

}
