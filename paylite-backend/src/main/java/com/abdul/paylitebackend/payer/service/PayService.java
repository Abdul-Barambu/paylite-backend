package com.abdul.paylitebackend.payer.service;

import com.abdul.paylitebackend.payer.Dto.PayerDetailsDto;
import com.abdul.paylitebackend.payer.entities.PayerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayerService payerService;

    public ResponseEntity<Object> makePayment(PayerDetailsDto payerDetailsDto){
        return payerService.paymentDetails(
                new PayerDetails(
                        payerDetailsDto.getName(),
                        payerDetailsDto.getEmail(),
                        payerDetailsDto.getPhoneNumber(),
                        payerDetailsDto.getAmount(),
                        payerDetailsDto.getService(),
                        payerDetailsDto.getSchool_id(),
                        null // Reference number will be generated in the service layer
                )
        );
    }
}
