package com.abdul.paylitebackend.school.controller;

import com.abdul.paylitebackend.school.service.SchoolRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/verify-school")
@RequiredArgsConstructor
public class AccountVerificationController {

    private final SchoolRegistration schoolRegistration;

    @GetMapping
    public ResponseEntity<Object> verifySchool(@RequestParam("verifyAccount") String verificationToken){
        return schoolRegistration.verifyAccount(verificationToken);
    }
}
