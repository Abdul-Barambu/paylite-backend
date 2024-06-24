package com.abdul.paylitebackend.flutterwave;

import com.abdul.paylitebackend.payer.Dto.PayerDetailsDto;
import com.abdul.paylitebackend.payer.service.PayService;
import com.abdul.paylitebackend.school.entity.NumberOfSuccessfulTransactions;
import com.abdul.paylitebackend.school.entity.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping(path = "/getNumberOfTransactions")
    public List<NumberOfSuccessfulTransactions> getAllTransactions() {
        return flutterwaveService.getAllTransactions();
    }

    @GetMapping(path = "/getNumberOfTransactions/{school_id}")
    public NumberOfSuccessfulTransactions findById(@PathVariable Long school_id) {
        return flutterwaveService.findById(school_id);
    }

    @GetMapping(path = "/checkBalance/{school_id}")
    public Wallet displayBalance(@PathVariable Long school_id){
        return flutterwaveService.displayBalance(school_id);
    }


}
