package com.abdul.paylitebackend.flutterwave;

import com.abdul.paylitebackend.config.FlutterWaveConfig;
import com.abdul.paylitebackend.payer.Dto.PayerDetailsDto;
import com.abdul.paylitebackend.school.entity.Schools;
import com.abdul.paylitebackend.school.entity.Wallet;
import com.abdul.paylitebackend.school.repository.SchoolRepository;
import com.abdul.paylitebackend.school.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
@RequiredArgsConstructor
public class FlutterWaveService {

    private final FlutterWaveConfig flutterWaveConfig;
    private final SchoolRepository schoolRepository;
    private final WalletRepository walletRepository;
    private static final Logger log = LoggerFactory.getLogger(FlutterWaveService.class);

    public ResponseEntity<Object> initiatePayment(Long schoolId, PayerDetailsDto payerDetailsDto) {
        Schools school = schoolRepository.findById(schoolId).orElse(null);
        if (school == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse("Error", "School not found"));
        }

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String json = "{"
                + "\"tx_ref\":\"" + System.currentTimeMillis() + "\","
                + "\"amount\":\"" + payerDetailsDto.getAmount() + "\","
                + "\"currency\":\"NGN\","
                + "\"redirect_url\":\"http://localhost:3000/callback\","
                + "\"payment_options\":\"card\","
                + "\"customer\":{\"email\":\"" + payerDetailsDto.getEmail() + "\",\"phonenumber\":\"" + payerDetailsDto.getPhoneNumber() + "\",\"name\":\"" + payerDetailsDto.getName() + "\"},"
                + "\"customizations\":{\"title\":\"School Payment\",\"description\":\"Payment for school fees\"},"
                + "\"meta\":{\"school_id\":\"" + schoolId + "\"}"
                + "}";

        RequestBody body = RequestBody.create(json, mediaType);
        Request request = new Request.Builder()
                .url(flutterWaveConfig.getApiUrl() + "/payments")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + flutterWaveConfig.getApiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            String responseBody = response.body().string();
            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse("Error", "Payment initiation failed"));
        }
    }


    public ResponseEntity<Object> handlePaymentCallback(Map<String, Object> paymentData) {
        // Extract payment status and other details from the callback data
        String status = (String) paymentData.get("status");
        String txRef = (String) paymentData.get("tx_ref");
        Double amount = Double.valueOf(paymentData.get("amount").toString());

        // Extract metadata and get school ID
        Map<String, Object> meta = (Map<String, Object>) paymentData.get("meta");
        Long schoolId = Long.valueOf((String) meta.get("school_id"));

        // Check if the payment was successful
        if ("successful".equalsIgnoreCase(status)) {
            // Find the school using the school ID
            Schools school = schoolRepository.findById(schoolId).orElse(null);
            if (school == null) {
                // Return an error response if the school is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse("Error", "School not found"));
            }

            // Find the wallet associated with the school
            Wallet wallet = walletRepository.findById(school.getId()).orElse(null);
            if (wallet == null) {
                // Return an error response if the wallet is not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse("Error", "Wallet not found"));
            }

            // Update the wallet balance
            wallet.setBalance(wallet.getBalance() + amount);
            walletRepository.save(wallet);

            // Return a success response
            return ResponseEntity.ok(successResponse("Success", "Payment successful and wallet updated"));
        } else {
            // Return an error response if the payment was not successful
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse("Error", "Payment not successful"));
        }
    }

    // Helper method to generate an error response
       private Map<String, Object> errorResponse(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }

    private Map<String, Object> successResponse(String status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);

        response.put("data", data);

        return response;
    }

    public ResponseEntity<Object> verifyTransaction(String transactionId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(flutterWaveConfig.getApiUrl() + "/transactions/" + transactionId + "/verify")
                .get()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + flutterWaveConfig.getApiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            if (!response.isSuccessful()) {
                return ResponseEntity.status(response.code()).body(errorResponse("Error", "Transaction verification failed: " + responseBody));
            }
            return ResponseEntity.ok(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse("Error", "Transaction verification failed: " + e.getMessage()));
        }
    }
}
