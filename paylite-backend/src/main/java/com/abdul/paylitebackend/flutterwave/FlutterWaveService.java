package com.abdul.paylitebackend.flutterwave;

import com.abdul.paylitebackend.config.FlutterWaveConfig;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlutterWaveService {

    private final FlutterWaveConfig flutterWaveConfig;
    private final SchoolRepository schoolRepository;
    private final WalletRepository walletRepository;

    public ResponseEntity<Object> initiatePayment(Long schoolId, Double amount) {
        Schools school = schoolRepository.findById(schoolId).orElse(null);
        if (school == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse("Error", "School not found"));
        }

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        String json = "{"
                + "\"tx_ref\":\"" + System.currentTimeMillis() + "\","
                + "\"amount\":\"" + amount + "\","
                + "\"currency\":\"NGN\","
                + "\"redirect_url\":\"http://yourdomain.com/callback\","
                + "\"payment_options\":\"card\","
                + "\"customer\":{\"email\":\"" + school.getEmail() + "\",\"phonenumber\":\"" + school.getPhoneNumber() + "\",\"name\":\"" + school.getName() + "\"},"
                + "\"customizations\":{\"title\":\"School Payment\",\"description\":\"Payment for school fees\"}"
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
        String status = (String) paymentData.get("status");
        if ("successful".equalsIgnoreCase(status)) {
            String txRef = (String) paymentData.get("tx_ref");
            Double amount = Double.valueOf(paymentData.get("amount").toString());
            String email = (String) paymentData.get("customer.email");

            Schools school = schoolRepository.findByEmail(email).orElse(null);
            if (school != null) {
                Wallet wallet = walletRepository.findById(school.getId()).orElse(null);
                if (wallet != null) {
                    wallet.setBalance(wallet.getBalance() + amount);
                    walletRepository.save(wallet);
                }
            }
            return ResponseEntity.ok(successResponse("Success", "Payment successful and wallet updated"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse("Error", "Payment not successful"));
        }
    }

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
}
