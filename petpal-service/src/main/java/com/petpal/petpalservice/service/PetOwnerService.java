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
import com.petpal.petpalservice.model.dto.VisibilityRequestDto;
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
  private final PetOwnerMapper mapper;

  public PetOwnerService(PetOwnerRepository repository, PetOwnerMapper mapper) {
    this.repository = repository;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.mapper = mapper;
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
  public void updateVisibility(VisibilityRequestDto dto, String email) {
    PetOwner petOwner = repository.findByOwnerEmail(email);
    petOwner.setProfileVisible(dto.isProfileVisible());
    petOwner.setPostVisible(dto.isPostVisible());
    petOwner.setPetVisible(dto.isPetVisible());
    repository.save(petOwner);
  }
  public PetOwnerResponseDto updatePersonalInfo(PersonalInfoDto dto, String email) {
    PetOwner petOwner = repository.findByOwnerEmail(email);
    // I want to assign attribute only if the value is not null
    if (dto.getOwnerDescription() != null) petOwner.setOwnerDescription(dto.getOwnerDescription());
    if (dto.getOwnerLocation() != null) petOwner.setOwnerLocation(dto.getOwnerLocation());
    if (dto.getOwnerFullName() != null) petOwner.setOwnerFullName(dto.getOwnerFullName());
    if (dto.getOwnerImage() != null) petOwner.setOwnerImage(dto.getOwnerImage());
    if (dto.getOwnerContactInfo() != null) petOwner.setOwnerContactInfo(dto.getOwnerContactInfo());
    repository.save(petOwner);
    return mapper.entityToDto(petOwner);
  }
}
