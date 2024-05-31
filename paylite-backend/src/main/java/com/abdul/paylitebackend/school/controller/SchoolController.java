package com.abdul.paylitebackend.school.controller;

import com.abdul.paylitebackend.school.dto.SchoolDto;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.service.SchoolRegistration;
import com.abdul.paylitebackend.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/register/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolRegistration schoolRegistration;
    private final SchoolService schoolService;


    @PostMapping(path = "addSchool")
    public ResponseEntity<Object> registerSchool(@RequestBody SchoolDto schoolDto){
        return schoolRegistration.registerSchool(schoolDto);
    }

    @GetMapping(path = "/getAllSchools")
    public List<Schools> getAllSchools(){
        return schoolService.getAllSchools();
    }
}
