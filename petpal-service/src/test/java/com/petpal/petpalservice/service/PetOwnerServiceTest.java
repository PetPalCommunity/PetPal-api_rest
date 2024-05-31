package com.petpal.petpalservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Equals;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;

import com.petpal.petpalservice.exception.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetOwnerMapper;
import com.petpal.petpalservice.model.dto.PetOwnerResponseDto;
import com.petpal.petpalservice.model.dto.VisibilityRequestDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PetOwnerServiceTest {

  @Mock
  private PetOwnerRepository repository;

  @InjectMocks
  private PetOwnerService service;

  @Mock 
  private PetOwnerMapper mapper = new PetOwnerMapper();

  @Test
  public void updateVisibilityTest() {
    // Given
    PetOwner owner = new PetOwner();
    owner.setId(1);
    owner.setOwnerName("John Doe");
    owner.setOwnerSex("M");
    owner.setOwnerAge(30);
    owner.setOwnerPhone(1234567890);
    owner.setOwnerEmail("saelcc03@gmail.com");
    owner.setOwnerPassword("1234");

    VisibilityRequestDto dtoafter = new VisibilityRequestDto();
    dtoafter.setProfileVisible(false);
    dtoafter.setPostVisible(false);
    dtoafter.setPetVisible(false);
    repository.save(owner);
    when(repository.save(any(PetOwner.class))).thenReturn(owner);
    when(repository.findByOwnerEmail(anyString())).thenReturn(Optional.of(owner));

    VisibilityRequestDto response = service.updateVisibility(dtoafter, "saelcc03@gmail.com");
    assertNotNull(response);
    assertFalse(owner.isProfileVisible());
    assertFalse(owner.isPostVisible());
    assertFalse(owner.isPetVisible());
  }

}
