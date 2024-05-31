package com.abdul.paylitebackend.school.service;

import com.abdul.paylitebackend.emailVal.EmailValidator;
import com.abdul.paylitebackend.school.dto.SchoolDto;
import com.abdul.paylitebackend.school.entity.SchoolRole;
import com.abdul.paylitebackend.school.entity.Schools;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchoolRegistration {

    private final EmailValidator emailValidator;
    private final SchoolService schoolService;


    public ResponseEntity<Object> registerSchool(SchoolDto schoolDto){
        boolean isEmailValid = emailValidator.test(schoolDto.getEmail());
        if (!isEmailValid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailNotValid("error", "Email not valid"));
        }
        return schoolService.signUp(
                new Schools(
                        schoolDto.getName(),
                        schoolDto.getEmail(),
                        schoolDto.getPassword(),
                        schoolDto.getBalance(),
                        SchoolRole.SCHOOL
                )
        );
    }


    //    Response
    public Map<String, Object> emailNotValid(String status, String message){
        Map<String, Object> response = new HashMap<>();
        response.put("error", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }
}
