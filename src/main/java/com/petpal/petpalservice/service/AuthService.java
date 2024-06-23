package com.petpal.petpalservice.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petpal.petpalservice.model.dto.AuthRequestDTO;
import com.petpal.petpalservice.model.dto.AuthResponseDTO;
import com.petpal.petpalservice.model.dto.RegisterRequestDTO;
import com.petpal.petpalservice.model.dto.ResetPassRequestDTO;
import com.petpal.petpalservice.model.dto.UserResponseDTO;
import com.petpal.petpalservice.model.dto.VerificationRequestDTO;
import com.petpal.petpalservice.exceptions.BadRequestException;
import com.petpal.petpalservice.mapper.UserMapper;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.repository.TokenRepository;
import com.petpal.petpalservice.repository.UserRepository;
import com.petpal.petpalservice.repository.VetRepository;
import com.petpal.petpalservice.security.JwtService;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.Token;
import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.model.entity.VerificationToken;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.model.enums.Role;
import com.petpal.petpalservice.model.enums.TokenType;
import java.util.Map;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PetOwnerRepository petOwnerRepository;
    private final VetRepository vetRepository;
    private final TokenRepository tokenRepository;
    private final VerificationTokenService verificationTokenService;

    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO register(RegisterRequestDTO request) {

        boolean emailAlreadyExists = userRepository.existsByEmail(request.getEmail());
        if (emailAlreadyExists) {
            throw new BadRequestException("El email ya está siendo usado por otro usuario.");
        }
        boolean usernameAlreadyExists = userRepository.existsByAlias(request.getAlias());
        if (usernameAlreadyExists) {
            throw new BadRequestException("El alias ya está siendo usado por otro usuario.");
        }
        
        User user = userMapper.convertToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(false);
        user.setCreatedAt(java.time.LocalDate.now());
        
        if (request.getRole().equals(Role.PETOWNER)) {
            PetOwner petOwner = new PetOwner();
            BeanUtils.copyProperties(user, petOwner);
            petOwnerRepository.save(petOwner);
            user = petOwner;
        } else if (request.getRole().equals(Role.VET)) {
            Vet vet = new Vet();
            BeanUtils.copyProperties(user, vet);
            vetRepository.save(vet);
            user = vet;
        }
        // Quitar el comentario para enviar el correo de verificación
        String token = verificationTokenService.createVerificationToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getFirstName() + " " + user.getLastName());
        variables.put("token", token);
        emailSenderService.sendEmail(user.getEmail(),"Verificación de correo electrónico","verificationEmail",variables); 
        return userMapper.convertToDTO(user);
    }

    @Transactional
    public AuthResponseDTO authenticate(AuthRequestDTO request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        User user = userRepository.findUserByEmail(request.getEmail())
            .orElseThrow();
        revokeAllUserTokens(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);     
        saveUserToken(user, jwtToken);
        return new AuthResponseDTO(jwtToken, refreshToken, userMapper.convertToDTO(user));
    }

    @Transactional 
    public UserResponseDTO verifyEmail(String token) {
        VerificationToken verificationTokenOpt  = verificationTokenService.getVerificationToken(token)
            .orElseThrow(() -> new BadRequestException("El token es inválido"));

        if (verificationTokenOpt.getExpirationDate().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            verificationTokenService.deleteVerificationToken(token);
            throw new BadRequestException("El token ha expirado");
        }

        User user = verificationTokenOpt.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenService.deleteVerificationToken(token);
        return userMapper.convertToDTO(user);
    }

    @Transactional
    public void resendVerificationEmail(VerificationRequestDTO verificationRequestDTO) {
        User user = userRepository.findUserByEmail(verificationRequestDTO.getEmail())
            .orElseThrow(() -> new BadRequestException("El usuario no existe"));
        if (user.isEnabled()) {
            throw new BadRequestException("El usuario ya ha sido verificado");
        }
        // eliminamos el token anterior
        verificationTokenService.deleteVerificationTokenByUser(user.getEmail());
        String token = verificationTokenService.createVerificationToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getFirstName() + " " + user.getLastName());
        variables.put("token", token);
        emailSenderService.sendEmail(user.getEmail(),"Verificación de correo electrónico","verificationEmail",variables);
    }

    @Transactional
    public void sendPasswordResetEmail(VerificationRequestDTO verificationRequestDTO) {
        User user = userRepository.findUserByEmail(verificationRequestDTO.getEmail())
            .orElseThrow(() -> new BadRequestException("El usuario no existe"));
        String token = verificationTokenService.createVerificationToken(user);
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", user.getFirstName() + " " + user.getLastName());
        variables.put("token", token);
        emailSenderService.sendEmail(user.getEmail(),"Recuperación de contraseña","passwordResetEmail",variables);
    }

    @Transactional
    public void resetPassword(ResetPassRequestDTO resetPassRequestDTO) {
        VerificationToken verificationTokenOpt = verificationTokenService.getVerificationToken(resetPassRequestDTO.getToken())
            .orElseThrow(() -> new BadRequestException("El token es inválido"));

        if (verificationTokenOpt.getExpirationDate().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
            verificationTokenService.deleteVerificationToken(resetPassRequestDTO.getToken());
            throw new BadRequestException("El token ha expirado");
        }

        User user = verificationTokenOpt.getUser();
        user.setPassword(passwordEncoder.encode(resetPassRequestDTO.getNewPassword()));
        userRepository.save(user);
        verificationTokenService.deleteVerificationToken(resetPassRequestDTO.getToken());
    } 

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, StreamWriteException, DatabindException, java.io.IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
        return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
        User user = this.userRepository.findUserByEmail(userEmail)
                .orElseThrow();
        if (jwtService.isTokenValid(refreshToken, user)) {
            String accessToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);
            AuthResponseDTO authResponse = new AuthResponseDTO(accessToken, refreshToken, userMapper.convertToDTO(user));
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = new Token();
        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        tokenRepository.save(token);
    }
    
    private void revokeAllUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
          return;
        validUserTokens.forEach(token -> {
          token.setExpired(true);
          token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
      }
    
}
