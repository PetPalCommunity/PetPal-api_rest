package com.petpal.petpalservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.petpal.petpalservice.model.dto.PetRequestDto;
import com.petpal.petpalservice.model.entity.Pet;
import lombok.AllArgsConstructor;
import com.petpal.petpalservice.model.dto.PetResponseDto;
import com.petpal.petpalservice.model.dto.ReminderRequestDto;
import com.petpal.petpalservice.model.dto.ReminderResponseDto;
import com.petpal.petpalservice.model.entity.Reminder;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Component
public class PetMapper {
    private final ModelMapper modelMapper;

    public Pet dtoToEntityPet(PetRequestDto dto) {
        return modelMapper.map(dto, Pet.class);
    }

    public PetResponseDto entityToDtoPet(Pet pet) {
        return modelMapper.map(pet, PetResponseDto.class);
    }

    public List<PetResponseDto> entityToListDtoPet(List<Pet> pets) {
        return pets.stream()
        .map(this::entityToDtoPet)
        .toList();
    }

    public Reminder dtoToEntityReminder(ReminderRequestDto dto) {
        return modelMapper.map(dto, Reminder.class);
    }

    public ReminderResponseDto entityToDtoReminder(Reminder reminder) {
        return modelMapper.map(reminder, ReminderResponseDto.class);
    }

    public List<ReminderResponseDto> entityToListDtoReminder(Set<Reminder> reminders) {
        return reminders.stream()
        .map(this::entityToDtoReminder)
        .toList();
    }

    
}
