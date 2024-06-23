package com.petpal.petpalservice.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.model.entity.VerificationToken;
import com.petpal.petpalservice.repository.VerificationTokenRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    @Value("${verification.token.expiration}")
    private long tokenExpiration;

    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public String createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpirationDate(LocalDateTime.now().plusSeconds(tokenExpiration/ 1000));
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public Optional<VerificationToken> getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);
    }

    public void deleteVerificationToken(String token) {
        verificationTokenRepository.deleteByToken(token);
    }

    public void deleteVerificationTokenByUser(String email) {
        verificationTokenRepository.deleteByUserEmail(email);
    }   
}
