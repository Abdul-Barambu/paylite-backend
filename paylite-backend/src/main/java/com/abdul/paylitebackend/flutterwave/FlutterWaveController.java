package com.abdul.paylitebackend.flutterwave;

import com.abdul.paylitebackend.payer.Dto.PayerDetailsDto;
import com.abdul.paylitebackend.payer.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class FlutterWaveController {

    private final FlutterWaveService flutterwaveService;
    private final PayService payService;

    @PostMapping("/initiate/{schoolId}")
    public ResponseEntity<Object> initiatePayment(@PathVariable Long schoolId, @RequestBody PayerDetailsDto payerDetailsDto) {
        payService.makePayment(payerDetailsDto);
        return flutterwaveService.initiatePayment(schoolId, payerDetailsDto);
    }

    @PostMapping("/callback")
    public ResponseEntity<Object> handlePaymentCallback(@RequestBody Map<String, Object> paymentData) {
        return flutterwaveService.handlePaymentCallback(paymentData);
    }
}
