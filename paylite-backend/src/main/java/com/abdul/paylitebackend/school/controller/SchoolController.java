package com.abdul.paylitebackend.school.controller;

import com.abdul.paylitebackend.school.dto.SchoolDto;
import com.abdul.paylitebackend.school.service.SchoolRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/register/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolRegistration schoolRegistration;


    @PostMapping
    public ResponseEntity<Object> registerSchool(@RequestBody SchoolDto schoolDto){
        return schoolRegistration.registerSchool(schoolDto);
    }

}
