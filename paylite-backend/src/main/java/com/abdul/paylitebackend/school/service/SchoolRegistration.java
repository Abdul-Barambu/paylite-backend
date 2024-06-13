package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.emailVal.EmailValidator;
import com.abdul.paylitebackend.school.dto.SchoolDto;
import com.abdul.paylitebackend.school.entity.AccountVerification;
import com.abdul.paylitebackend.school.entity.SchoolRole;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.entity.Wallet;
import com.abdul.paylitebackend.school.repository.AccountVerificationRepository;
import com.abdul.paylitebackend.school.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchoolRegistration {

    private final EmailValidator emailValidator;
    private final SchoolService schoolService;
    private final WalletService walletService;
    private final AccountVerificationService accountVerificationService;


    public ResponseEntity<Object> registerSchool(SchoolDto schoolDto) {
        boolean isEmailValid = emailValidator.test(schoolDto.getEmail());
        if (!isEmailValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailNotValid("error", "Email not valid"));
        }
        return schoolService.signUp(
                new Schools(
                        schoolDto.getName(),
                        schoolDto.getEmail(),
                        schoolDto.getPassword(),
                        SchoolRole.SCHOOL
                )
        );
    }

//   account verification

    public ResponseEntity<Object> verifyAccount(String verificationToken) {
        try {
            AccountVerification accountVerification = accountVerificationService.getVerificationToken(verificationToken).orElseThrow(() -> new IllegalStateException("Token not found"));

            if (accountVerification.getConfirmedAt() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("error", "School already confirmed"));
            }

            LocalDateTime expired = accountVerification.getExpiresAt();
            if (expired.isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("error", "Token has expired, account not verified"));
            }

            accountVerificationService.setConfirmedAt(verificationToken);
            schoolService.enableSchool(accountVerification.getSchools().getEmail());

            // Create wallet after successful account verification
            Wallet wallet = new Wallet(
                    accountVerification.getSchools(),
                    0.00
            );
            walletService.saveWallet(wallet);

            return ResponseEntity.ok(success("success", "Your account has been verified successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("error", e.getMessage()));
        }
    }


    //    Response
    public Map<String, Object> emailNotValid(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }

    public Map<String, Object> error(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }

    public Map<String, Object> success(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }
}
