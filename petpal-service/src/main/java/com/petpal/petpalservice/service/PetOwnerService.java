package com.petpal.petpalservice.service;


import com.petpal.petpalservice.exception.DuplicateResourceException;
import com.petpal.petpalservice.exception.InvalidEmailFormatException;
import com.petpal.petpalservice.exception.MissingRequiredFieldException;
import com.petpal.petpalservice.mapper.PetOwnerMapper;
import com.petpal.petpalservice.exception.InvalidCredentialsException;
import com.petpal.petpalservice.model.dto.PersonalInfoDto;
import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.PetOwnerResponseDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.dto.StorageService;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import java.lang.reflect.Field;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
@Transactional
public class PetOwnerService {
  private final PetOwnerRepository repository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final PetOwnerMapper mapper;
  private final StorageService storageService;

  public PetOwnerService(PetOwnerRepository repository, PetOwnerMapper mapper, StorageService storageService) {
    this.repository = repository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.mapper = mapper;
    this.storageService = storageService;
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
    PetOwner petOwner = repository.findByOwnerEmail(dto.getOwnerEmail())
    .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

    if (petOwner == null || !passwordEncoder.matches(dto.getOwnerPassword(), petOwner.getOwnerPassword())) {
      throw new InvalidCredentialsException("Invalid email or password");
    }
    return petOwner;
  }
  public PetOwnerResponseDto updatePersonalInfo(PersonalInfoDto dto, String email) {
    PetOwner petOwner = repository.findByOwnerEmail(email)
    .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
    Field[] fields = dto.getClass().getDeclaredFields();
    for(Field field: fields){
      field.setAccessible(true);
      try {
        Object value = field.get(dto);
        if(value != null){
          Field perInfo = petOwner.getClass().getDeclaredField(field.getName());
          perInfo.setAccessible(true);
          ReflectionUtils.setField(perInfo,petOwner,value);
          perInfo.setAccessible(false);
        }
        field.setAccessible(false);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    repository.save(petOwner);
    return mapper.entityToDto(petOwner);
  }
  public PetOwnerResponseDto updateProfilePicture(String email, String path) {
    PetOwner petOwner = repository.findByOwnerEmail(email)
    .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
    petOwner.setOwnerImage(path);
    repository.save(petOwner);
    return mapper.entityToDto(petOwner);
  }
}
