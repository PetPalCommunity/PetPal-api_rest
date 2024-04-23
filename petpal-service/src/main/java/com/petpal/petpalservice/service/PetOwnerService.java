package com.petpal.petpalservice.service;

import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class PetOwnerService {
    private final PetOwnerRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PetOwnerService(PetOwnerRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public PetOwner createPetOwner(PetOwnerRequestDto dto) {
        if (dto.getOwner_name() == null || dto.getOwner_name().isEmpty()) {
            throw new MissingRequiredFieldException("El nombre es obligatorio");
        }

        if (dto.getOwner_email() == null || !EMAIL_PATTERN.matcher(dto.getOwner_email()).matches()) {
            throw new InvalidEmailFormatException("Formato de correo electrónico inválido");
        }


        if (repository.existsByOwnerEmail(dto.getOwner_email())) {
            throw new RuntimeException("El correo electrónico ya está en uso");
        }

        if (repository.existsByOwnerPhone(dto.getOwner_phone())) {
            throw new RuntimeException("El número de teléfono ya está en uso");
        }

        PetOwner petOwner = new PetOwner();
        petOwner.setOwner_name(dto.getOwner_name());
        petOwner.setOwner_sex(dto.getOwner_sex());
        petOwner.setOwner_age(dto.getOwner_age());
        petOwner.setOwner_email(dto.getOwner_email());
        petOwner.setOwner_phone(dto.getOwner_phone());
        petOwner.setOwner_password(passwordEncoder.encode(dto.getOwner_password()));

        return repository.save(petOwner);
    }
}
