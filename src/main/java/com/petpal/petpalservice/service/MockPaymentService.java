package com.petpal.petpalservice.service;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.PaymentAPIResponseDTO;
import com.petpal.petpalservice.model.dto.PaymentRequestDTO;



@Component
public class MockPaymentService {

    public PaymentAPIResponseDTO processPayment(PaymentRequestDTO request) {
        Random random = new Random();
        boolean paymentSuccess = random.nextBoolean();

        simulateDelay(2000);
        if (paymentSuccess) {
            return new PaymentAPIResponseDTO(true,generateTransactionId() , "Payment successful");
        } else {
            return new PaymentAPIResponseDTO(false, null, "Payment failed");
        }
    }

    private String generateTransactionId() {
        long timestamp = System.currentTimeMillis();
        String suffix = generateRandomNumericSuffix(6);
        return timestamp + suffix;
    }

    private String generateRandomNumericSuffix(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }

    private void simulateDelay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted while sleeping", e);
        }
    }
}