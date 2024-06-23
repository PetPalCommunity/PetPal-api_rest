package com.petpal.petpalservice.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.model.dto.ChangePassRequestDTO;
import com.petpal.petpalservice.model.dto.VisibilityRequestDTO;
import com.petpal.petpalservice.exceptions.BadRequestException;
import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void changePassword(ChangePassRequestDTO request) {
        User user = getUserByAuth();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void updateVisibility(VisibilityRequestDTO dto) {
        User user = getUserByAuth();
        user.setProfileVisible(dto.isProfileVisible());
        user.setPostVisible(dto.isPostVisible());
        user.setPetVisible(dto.isPetVisible());
        userRepository.save(user);
    }

    public User getUserByAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userRepository.findUserByEmail(userDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
