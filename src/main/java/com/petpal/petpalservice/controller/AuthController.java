package com.petpal.petpalservice.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalservice.model.dto.AuthRequestDTO;
import com.petpal.petpalservice.model.dto.AuthResponseDTO;
import com.petpal.petpalservice.model.dto.RegisterRequestDTO;
import com.petpal.petpalservice.model.dto.ResetPassRequestDTO;
import com.petpal.petpalservice.model.dto.UserResponseDTO;
import com.petpal.petpalservice.model.dto.VerificationRequestDTO;
import com.petpal.petpalservice.service.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> register(@Validated @RequestBody RegisterRequestDTO request) {
    UserResponseDTO responseDTO = authService.register(request);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> authenticate(@Validated @RequestBody AuthRequestDTO request) {
    AuthResponseDTO responseDTO = authService.authenticate(request);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    authService.refreshToken(request, response);
  }
  
  @PutMapping("/verify")
  public ResponseEntity<UserResponseDTO> verifyEmail(@RequestParam String token) {
    UserResponseDTO responseDTO = authService.verifyEmail(token);
    return new ResponseEntity<>(responseDTO, HttpStatus.OK);
  }

  @PostMapping("/resend-verification")
  public ResponseEntity<String> resendVerification(@Validated @RequestBody VerificationRequestDTO verificationRequestDTO) {
    authService.resendVerificationEmail(verificationRequestDTO);
    return new ResponseEntity<>("Email de verificación reenviado", HttpStatus.OK);
  }

  @PostMapping("/send-forgot-password")
  public ResponseEntity<String> forgotPassword(@Validated @RequestBody VerificationRequestDTO verificationRequestDTO) {
    authService.sendPasswordResetEmail(verificationRequestDTO);
    return new ResponseEntity<>("Email de recuperación de contraseña enviado", HttpStatus.OK);
  }

  @PutMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@Validated @RequestBody ResetPassRequestDTO resetPassRequestDTO) {
    authService.resetPassword(resetPassRequestDTO);
    return new ResponseEntity<>("Contraseña restablecida", HttpStatus.OK);
  }


}