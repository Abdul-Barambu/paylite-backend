package com.abdul.paylitebackend.payer.service;

import com.abdul.paylitebackend.payer.entities.PayerDetails;
import com.abdul.paylitebackend.payer.repository.PayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PayerService {

    private final PayerRepository payerRepository;

    public ResponseEntity<Object> paymentDetails(PayerDetails payerDetails){
        payerRepository.save(payerDetails);

        return ResponseEntity.ok(registrationSuccessful("success", "Your payment has been initialize"));
    }

    public List<PayerDetails> getAllPays(){
        return payerRepository.findAll();
    }

//    TODO: create a reference number for each payment as a unique field

    public Map<String, Object> registrationSuccessful(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }
}
