package com.abdul.paylitebackend.payer.controller;

import com.abdul.paylitebackend.payer.entities.PayerDetails;
import com.abdul.paylitebackend.payer.service.PayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/payer/")
@RequiredArgsConstructor
public class PayerController {

    private final PayerService payerService;

    @GetMapping(path = "/getAllPays")
    public List<PayerDetails> getAllPays(){
        return payerService.getAllPays();
    }

}
