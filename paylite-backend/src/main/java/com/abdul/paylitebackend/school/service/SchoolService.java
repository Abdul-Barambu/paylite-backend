package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.entity.Wallet;
import com.abdul.paylitebackend.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchoolService implements UserDetailsService {

    private final SchoolRepository schoolRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final WalletService walletService;

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

//        wallet
        Wallet wallet = new Wallet(
                schools,
                0.00
        );

        walletService.saveWallet(wallet);

        return ResponseEntity.ok(registrationSuccessful("success", "Your registration has been successfully"));
    }

    public List<Schools> getAllSchools() {
        return schoolRepository.findAll();
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
