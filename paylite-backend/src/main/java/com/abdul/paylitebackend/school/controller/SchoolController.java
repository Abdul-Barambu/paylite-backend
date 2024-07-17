package com.abdul.paylitebackend.school.controller;

import com.abdul.paylitebackend.school.dto.ChangePasswordDto;
import com.abdul.paylitebackend.school.dto.ForgotPasswordEmailDto;
import com.abdul.paylitebackend.school.dto.SchoolDto;
import com.abdul.paylitebackend.school.dto.UpdateSchoolDto;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.service.ForgotPasswordService;
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
    private final ForgotPasswordService forgotPasswordService;


    @PostMapping(path = "/addSchool")
    public ResponseEntity<Object> registerSchool(@RequestBody SchoolDto schoolDto){
        return schoolRegistration.registerSchool(schoolDto);
    }

    @GetMapping(path = "/getAllSchools")
    public List<Schools> getAllSchools(){
        return schoolService.getAllSchools();
    }

    @PutMapping(path = "/updateSchool/{id}")
    public ResponseEntity updateSchool(@PathVariable Long id, @RequestBody UpdateSchoolDto updateSchoolDto){
        return schoolService.updateSchool(id, updateSchoolDto);
    }

    @DeleteMapping(path = "/deleteSchool/{id}")
    public ResponseEntity deleteSchool(@PathVariable Long id){
        return schoolService.deleteSchool(id);
    }

    @PostMapping(path = "/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return schoolService.changePassword(changePasswordDto);
    }

    @PostMapping(path = "/forgot-password")
    public String randomNumber(@RequestBody ForgotPasswordEmailDto forgotPasswordEmailDto){
        return forgotPasswordService.generateOtpNumber(forgotPasswordEmailDto);
    }

}
