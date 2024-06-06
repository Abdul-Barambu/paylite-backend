package com.abdul.paylitebackend.flutterwave;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class FlutterWaveController {

    private final FlutterWaveService flutterwaveService;

    @PostMapping("/initiate/{schoolId}")
    public ResponseEntity<Object> initiatePayment(@PathVariable Long schoolId, @RequestParam Double amount) {
        return flutterwaveService.initiatePayment(schoolId, amount);
    }

    @PostMapping("/callback")
    public ResponseEntity<Object> handlePaymentCallback(@RequestBody Map<String, Object> paymentData) {
        return flutterwaveService.handlePaymentCallback(paymentData);
    }
}
