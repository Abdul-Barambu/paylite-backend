package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.school.dto.UpdateSchoolDto;
import com.abdul.paylitebackend.school.entity.AccountVerification;
import com.abdul.paylitebackend.school.entity.NumberOfSuccessfulTransactions;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.entity.Wallet;
import com.abdul.paylitebackend.school.repository.AccountVerificationRepository;
import com.abdul.paylitebackend.school.repository.NumberOfSuccessfulTransactionsRepository;
import com.abdul.paylitebackend.school.repository.SchoolRepository;
import com.abdul.paylitebackend.school.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SchoolService implements UserDetailsService {

    private final SchoolRepository schoolRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final WalletRepository walletRepository;
    private final AccountVerificationRepository accountVerificationRepository;
    private final NumberOfSuccessfulTransactionsRepository numberOfSuccessfulTransactionsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return schoolRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
    }

    public ResponseEntity<Object> signUp(Schools schools) {
        boolean emailExist = schoolRepository.findByEmail(schools.getEmail()).isPresent();
        if (emailExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailExist("Error", "Email exists already"));
        }

        String passwordEncoded = bCryptPasswordEncoder.encode(schools.getPassword());
        schools.setPassword(passwordEncoded);

        schoolRepository.save(schools);

//        verification token
        String verificationToken = UUID.randomUUID().toString();
        AccountVerification accountVerification = new AccountVerification(
                verificationToken,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                schools
        );

        accountVerificationRepository.save(accountVerification);

        return ResponseEntity.ok(registrationSuccessful("success", "Your registration has been successfully, Please verify your account" +
                " using the url http://localhost:8080/api/v1/verify-school?verifyAccount=" + verificationToken));
    }

    public List<Schools> getAllSchools() {
        return schoolRepository.findAll();
    }

    public int enableSchool(String email) {
        return schoolRepository.enabledSchool(email);
    }

    public ResponseEntity<Object> updateSchool(Long id, UpdateSchoolDto updateSchoolDto) {
        Optional<Schools> schools = schoolRepository.findById(id);

        if (schools.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailExist("Error", "School Not Found"));
        }

        Schools school = schools.get();
        if (updateSchoolDto.getAddress() != null) {
            school.setAddress(updateSchoolDto.getAddress());
        }
        if (updateSchoolDto.getPhoneNumber() != null) {
            school.setPhoneNumber(updateSchoolDto.getPhoneNumber());
        }
        if (updateSchoolDto.getState() != null) {
            school.setState(updateSchoolDto.getState());
        }
        if (updateSchoolDto.getBankName() != null) {
            school.setBankName(updateSchoolDto.getBankName());
        }
        if (updateSchoolDto.getAccountName() != null) {
            school.setAccountName(updateSchoolDto.getAccountName());
        }
        if (updateSchoolDto.getAccountNumber() != null) {
            school.setAccountNumber(updateSchoolDto.getAccountNumber());
        }
        if (updateSchoolDto.getLogo() != null) {
            school.setLogo(updateSchoolDto.getLogo());
        }
        if (updateSchoolDto.getDocuments() != null) {
            school.setDocuments(updateSchoolDto.getDocuments());
        }

        schoolRepository.save(school);
        return ResponseEntity.ok(registrationSuccessful("Success", "School updated successfully"));
    }


    public ResponseEntity<Object> deleteSchool(Long id) {
        Optional<Schools> schools = schoolRepository.findById(id);

        if (schools.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailExist("Error", "School Not Found"));
        }

        Schools school = schools.get();

//        delete wallet table with instance id from school table
        Optional<Wallet> wallet = walletRepository.findById(school.getId());
        wallet.ifPresent(walletRepository::delete);

//        delete token verification table with instance id from school table
        Optional<AccountVerification> accountVerification = accountVerificationRepository.findById(school.getId());
        accountVerification.ifPresent(accountVerificationRepository::delete);

        Optional<NumberOfSuccessfulTransactions> numberOfSuccessfulTransactions = numberOfSuccessfulTransactionsRepository.findById(school.getId());
        numberOfSuccessfulTransactions.ifPresent(numberOfSuccessfulTransactionsRepository::delete);

        schoolRepository.delete(school);
        return ResponseEntity.ok(registrationSuccessful("Success", "School Deleted successfully"));

    }

    //    Response
    public Map<String, Object> emailExist(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }

    public Map<String, Object> registrationSuccessful(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }
}
