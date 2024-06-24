package com.abdul.paylitebackend.payer.service;

import com.abdul.paylitebackend.payer.entities.PayerDetails;
import com.abdul.paylitebackend.payer.repository.PayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PayerService {

    private final PayerRepository payerRepository;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int REFERENCE_NUMBER_LENGTH = 10;

    public ResponseEntity<Object> paymentDetails(PayerDetails payerDetails){
        payerDetails.setReferenceNumber(generateReferenceNumber());
        payerRepository.save(payerDetails);

        return ResponseEntity.ok(registrationSuccessful("success", "Your payment has been initialized with reference number: " + payerDetails.getReferenceNumber()));
    }

    public List<PayerDetails> getAllPays(){
        return payerRepository.findAll();
    }

    // Generate a random 10-character alphanumeric string
    private String generateReferenceNumber() {
        StringBuilder builder = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < REFERENCE_NUMBER_LENGTH; i++) {
            int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
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
