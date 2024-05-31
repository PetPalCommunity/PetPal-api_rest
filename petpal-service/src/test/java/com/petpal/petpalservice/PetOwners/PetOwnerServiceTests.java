package com.petpal.petpalservice.PetOwners;

import com.petpal.petpalservice.exception.*;
import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;
import com.petpal.petpalservice.service.PetOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PetOwnerServiceTests {
    @MockBean
    private PetOwnerRepository repository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoderMock;

    private PetOwnerService service;
    private BCryptPasswordEncoder passwordEncoder;


    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        service = new PetOwnerService(repository);
    }

    @Test
    public void testCreatePetOwnerWithMissingFields() {
        PetOwnerRequestDto dto = new PetOwnerRequestDto();


        assertThrows(MissingRequiredFieldException.class, () -> service.createPetOwner(dto));
    }

    @Test
    public void testCreatePetOwnerWithInvalidEmail() {
        PetOwnerRequestDto dto = new PetOwnerRequestDto();
        dto.setOwnerName("Sebastian");
        dto.setOwnerAge(23);
        dto.setOwnerSex("Male");
        dto.setOwnerPhone(953523994);
        dto.setOwnerEmail("sebas000802");
        dto.setOwnerPassword("password123");

        assertThrows(InvalidEmailFormatException.class, () -> service.createPetOwner(dto));
}

    @Test
    public void testCreatePetOwnerWithDuplicateEmailOrPhone() {
        PetOwnerRequestDto dto = new PetOwnerRequestDto();
        dto.setOwnerName("Sebastian");
        dto.setOwnerAge(23);
        dto.setOwnerSex("Male");
        dto.setOwnerPhone(953523994);
        dto.setOwnerEmail("sebas000802@gmail.com");
        dto.setOwnerPassword("password123");

        when(repository.existsByOwnerEmail(dto.getOwnerEmail())).thenReturn(true);
        when(repository.existsByOwnerPhone(dto.getOwnerPhone())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.createPetOwner(dto));
    }

    @Test
    public void testCreatePetOwnerWithValidData() {
        PetOwnerRequestDto dto = new PetOwnerRequestDto();
        dto.setOwnerName("Sebastian");
        dto.setOwnerAge(23);
        dto.setOwnerSex("Male");
        dto.setOwnerPhone(953523994);
        dto.setOwnerEmail("sebas000802@gmail.com");
        dto.setOwnerPassword("password123");

        when(repository.save(any(PetOwner.class))).thenReturn(new PetOwner());

        PetOwner result = service.createPetOwner(dto);

        assertNotNull(result);
    }

    @Test
    public void testValidateSignInWithInvalidCredentials() {
        SignInRequestDto dto = new SignInRequestDto();
        dto.setOwnerEmail("test@example.com");
        dto.setOwnerPassword("password123");

        when(repository.findByOwnerEmail(dto.getOwnerEmail())).thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () -> service.validateSignIn(dto));
    }

    @Test
    public void testValidateSignInWithValidCredentials() {
        SignInRequestDto dto = new SignInRequestDto();
        dto.setOwnerEmail("test@example.com");
        dto.setOwnerPassword("password123");

        PetOwner petOwner = new PetOwner();
        petOwner.setOwnerEmail(dto.getOwnerEmail());
        petOwner.setOwnerPassword(passwordEncoder.encode(dto.getOwnerPassword()));

        when(repository.findByOwnerEmail(dto.getOwnerEmail())).thenReturn(petOwner);
        when(passwordEncoderMock.matches(dto.getOwnerPassword(), petOwner.getOwnerPassword())).thenReturn(true);

        PetOwner result = service.validateSignIn(dto);

        assertEquals(petOwner, result);
    }
}