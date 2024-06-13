package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.school.entity.AccountVerification;
import com.abdul.paylitebackend.school.repository.AccountVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {

    private final AccountVerificationRepository accountVerificationRepository;

    public void saveVerificationToken(AccountVerification accountVerification) {
        accountVerificationRepository.save(accountVerification);
    }

    public Optional<AccountVerification> getVerificationToken(String verificationToken) {
        return accountVerificationRepository.findByVerificationToken(verificationToken);
    }

    public int setConfirmedAt(String verificationToken) {
        return accountVerificationRepository.updateConfirmedAt(verificationToken, LocalDateTime.now());
    }
}
