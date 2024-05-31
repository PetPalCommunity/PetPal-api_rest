package com.petpal.petpalservice.Vets;

import com.petpal.petpalservice.exception.*;
import com.petpal.petpalservice.model.dto.VetRequestDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.entity.Vet;
import com.petpal.petpalservice.repository.VetRepository;
import com.petpal.petpalservice.service.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class VetServiceTests {
    @MockBean
    private VetRepository repository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoderMock;

    private VetService service;
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
        service = new VetService(repository);
    }

    @Test
    public void testCreateVetWithMissingFields() {
        VetRequestDto dto = new VetRequestDto();

        assertThrows(MissingRequiredFieldException.class, () -> service.createVet(dto));
    }

    @Test
    public void testCreateVetWithInvalidEmail() {
        VetRequestDto dto = new VetRequestDto();
        dto.setVetName("Sebastian");
        dto.setVetLastname("Guevara");
        dto.setVetSex("Male");
        dto.setVetPhone(953523994);
        dto.setVetLocation("Lima");
        dto.setVetEmail("sebas000802");
        dto.setVetPassword("password123");
        dto.setVetLicenseNumber(123456);

        assertThrows(InvalidEmailFormatException.class, () -> service.createVet(dto));
    }

    @Test
    public void testCreateVetWithDuplicateEmailOrPhone() {
        VetRequestDto dto = new VetRequestDto();
        dto.setVetName("Sebastian");
        dto.setVetLastname("Guevara");
        dto.setVetSex("Male");
        dto.setVetPhone(953523994);
        dto.setVetLocation("Lima");
        dto.setVetEmail("sebas000802@gmail.com");
        dto.setVetPassword("password123");
        dto.setVetLicenseNumber(123456);

        when(repository.existsByVetEmail(dto.getVetEmail())).thenReturn(true);
        when(repository.existsByVetPhone(dto.getVetPhone())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.createVet(dto));
    }

    @Test
    public void testCreateVetWithValidData() {
        VetRequestDto dto = new VetRequestDto();
        dto.setVetName("Sebastian");
        dto.setVetLastname("Guevara");
        dto.setVetSex("Male");
        dto.setVetPhone(953523994);
        dto.setVetLocation("Lima");
        dto.setVetEmail("sebas000802@gmail.com");
        dto.setVetPassword("password123");
        dto.setVetLicenseNumber(123456);

        when(repository.save(any(Vet.class))).thenReturn(new Vet());

        Vet result = service.createVet(dto);

        assertNotNull(result);
    }

    @Test
    public void testValidateSignInWithInvalidCredentials() {
        SignInRequestDto dto = new SignInRequestDto();
        dto.setVetEmail("test@example.com");
        dto.setVetPassword("password123");

        when(repository.findByVetEmail(dto.getVetEmail())).thenReturn(null);

        assertThrows(InvalidCredentialsException.class, () -> service.validateSignIn(dto));
    }

    @Test
    public void testValidateSignInWithValidCredentials() {
        SignInRequestDto dto = new SignInRequestDto();
        dto.setVetEmail("test@example.com");
        dto.setVetPassword("password123");

        Vet vet = new Vet();
        vet.setVetEmail(dto.getVetEmail());
        vet.setVetPassword(passwordEncoder.encode(dto.getVetPassword()));

        when(repository.findByVetEmail(dto.getVetEmail())).thenReturn(vet);
        when(passwordEncoderMock.matches(dto.getVetPassword(), vet.getVetPassword())).thenReturn(true);

        Vet result = service.validateSignIn(dto);

        assertEquals(vet, result);
    }
}