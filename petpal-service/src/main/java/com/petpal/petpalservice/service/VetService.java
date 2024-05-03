package com.petpal.petpalservice.service;

import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.VetRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VetService {
    private final VetRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public VetService(VetRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Vet createVet(VetRequestDto dto) {
        if (dto.getVetName() == null || dto.getVetName().isEmpty() ||
                dto.getVetLastname() == null || dto.getVetLastname().isEmpty() ||
                dto.getVetSex() == null || dto.getVetSex().isEmpty() ||
                dto.getVetLicenseNumber() <= 0 || (int) Math.log10(dto.getVetLicenseNumber()) + 1 != 6 ||
                dto.getVetLocation() == null || dto.getVetLocation().isEmpty() ||
                dto.getVetPhone() <= 0 || (int) Math.log10(dto.getVetPhone()) + 1 != 9) {
            throw new MissingRequiredFieldException("Required field is missing");
        }

        if (dto.getVetEmail() == null || !dto.getVetEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new InvalidEmailFormatException("Invalid email format");
        }

        if (repository.existsByVetEmail(dto.getVetEmail()) ||
                repository.existsByVetPhone(dto.getVetPhone()) ||
                repository.existsByVetLicenseNumber(dto.getVetLicenseNumber())) {
            throw new DuplicateResourceException("Resource already exists");
        }

        Vet vet = new Vet();
        vet.setVetName(dto.getVetName());
        vet.setVetLastname(dto.getVetLastname());
        vet.setVetSex(dto.getVetSex());
        vet.setVetPhone(dto.getVetPhone());
        vet.setVetLocation(dto.getVetLocation());
        vet.setVetEmail(dto.getVetEmail());
        vet.setVetPassword(passwordEncoder.encode(dto.getVetPassword()));
        vet.setVetLicenseNumber(dto.getVetLicenseNumber());

        return repository.save(vet);
    }
}
