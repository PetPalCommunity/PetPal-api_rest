package com.petpal.petpalservice.mapper;

import lombok.AllArgsConstructor;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.RegisterRequestDTO;
import com.petpal.petpalservice.model.dto.UserResponseDTO;
import com.petpal.petpalservice.model.entity.User;

@Component
@AllArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public User convertToEntity(RegisterRequestDTO registerDTO){
        return  modelMapper.map(registerDTO, User.class);
    }

    public UserResponseDTO convertToDTO(User user){
        return  modelMapper.map(user, UserResponseDTO.class);
    }

    public List<UserResponseDTO> convertToListDTO(List<User> users){
        return users.stream()
        .map(this::convertToDTO)
        .toList();
    }
}