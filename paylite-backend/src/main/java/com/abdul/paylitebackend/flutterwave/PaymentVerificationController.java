package com.abdul.paylitebackend.payment;

import com.abdul.paylitebackend.flutterwave.FlutterWaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentVerificationController {

    private final FlutterWaveService flutterWaveService;

    @PostMapping("/verify-payment")
    public ResponseEntity<Object> verifyPayment(@RequestBody Map<String, String> request) {
        String txRef = request.get("txRef");
        String transactionId = request.get("transactionId");
        return flutterWaveService.verifyTransaction(transactionId);
    }
}
