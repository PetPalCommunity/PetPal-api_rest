package com.petpal.petpalservice.service;


import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.exception.InvalidCredentialsException;
import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.RecoverPasswordDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PetOwnerService {
    private final PetOwnerRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PetOwnerService(PetOwnerRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PetOwner createPetOwner(PetOwnerRequestDto dto) {
        if (dto.getOwnerName() == null || dto.getOwnerName().isEmpty() ||
                dto.getOwnerSex() == null || dto.getOwnerSex().isEmpty() ||
                dto.getOwnerAge() <= 0 ||
                dto.getOwnerPhone() <= 0 || (int) Math.log10(dto.getOwnerPhone()) + 1 != 9) {
            throw new MissingRequiredFieldException("Required field is missing");
        }

        if (dto.getOwnerEmail() == null || !dto.getOwnerEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidEmailFormatException("Invalid email format");
        }

        if (repository.existsByOwnerEmail(dto.getOwnerEmail()) ||
                repository.existsByOwnerPhone(dto.getOwnerPhone())) {
            throw new DuplicateResourceException("Resource already exists");
        }

        PetOwner petOwner = new PetOwner();
        petOwner.setOwnerName(dto.getOwnerName());
        petOwner.setOwnerSex(dto.getOwnerSex());
        petOwner.setOwnerAge(dto.getOwnerAge());
        petOwner.setOwnerEmail(dto.getOwnerEmail());
        petOwner.setOwnerPhone(dto.getOwnerPhone());
        petOwner.setOwnerPassword(passwordEncoder.encode(dto.getOwnerPassword()));

        return repository.save(petOwner);
    }

    public PetOwner validateSignIn(SignInRequestDto dto) {
        PetOwner petOwner = repository.findByOwnerEmail(dto.getOwnerEmail());

        if (petOwner == null || !passwordEncoder.matches(dto.getOwnerPassword(), petOwner.getOwnerPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return petOwner;
    }

    public void recoverPetOwnerPassword(RecoverPasswordDto dto) {
        PetOwner petOwner = repository.findByOwnerEmailAndOwnerPhone(dto.getOwnerEmail(), dto.getOwnerPhone());
        if (petOwner == null) {
            throw new InvalidCredentialsException("Invalid email or phone number");
        }
        petOwner.setOwnerPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(petOwner);
        System.out.println("Password updated successfully for Owner: " + petOwner.getOwnerName());
    }
}