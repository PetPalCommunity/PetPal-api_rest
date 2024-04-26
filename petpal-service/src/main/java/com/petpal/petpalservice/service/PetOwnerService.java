package com.petpal.petpalservice.service;

import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;
import com.petpal.petpalservice.exception.DuplicateResourceException;
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
        if (dto.getOwnerName() == null || dto.getOwnerName().isEmpty()) {
            throw new MissingRequiredFieldException("El nombre es obligatorio");
        }
        if(dto.getOwnerSex()==null || dto.getOwnerSex().isEmpty()){
            throw new MissingRequiredFieldException("El sexo es obligatorio");
        }
        if(dto.getOwnerAge() <=0){
            throw new MissingRequiredFieldException("La edad es obligatoria");
        }
        int ownerPhone = dto.getOwnerPhone();
        int digitCount = (int) Math.log10(ownerPhone) + 1;
        if (ownerPhone <= 0 ||  digitCount!=9) {
            throw new MissingRequiredFieldException("Numero de telefono invalido");
        }

        if (dto.getOwnerEmail() == null || !EMAIL_PATTERN.matcher(dto.getOwnerEmail()).matches()) {
            throw new InvalidEmailFormatException("Formato de correo electrónico inválido");
        }


        if (repository.existsByOwnerEmail(dto.getOwnerEmail())) {
            throw new DuplicateResourceException("El correo electrónico ya está en uso");
        }

        if (repository.existsByOwnerPhone(dto.getOwnerPhone())) {
            throw new DuplicateResourceException("El número de teléfono ya está en uso");
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
}
