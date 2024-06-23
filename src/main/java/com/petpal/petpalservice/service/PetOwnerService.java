package com.petpal.petpalservice.service;


import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PetOwnerMapper;
import com.petpal.petpalservice.model.dto.FollowerResponseDTO;
import com.petpal.petpalservice.model.dto.PetOwnerProfileDTO;
import com.petpal.petpalservice.model.dto.PetOwnerProfileUpdateDTO;
import com.petpal.petpalservice.model.dto.VisibilityRequestDTO;
import com.petpal.petpalservice.model.entity.Followers;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.repository.FollowersRepository;
import com.petpal.petpalservice.repository.PetOwnerRepository;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
public class PetOwnerService {
    private final PetOwnerRepository repository;

    private final FollowersRepository followersRepository;

    private final PetOwnerMapper mapper;

    private final UserService userService;

    @Transactional(readOnly = true)
    public PetOwnerProfileDTO getProfile(String alias) {
        User user = userService.getUserByAuth();
        PetOwner petOwner = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        if (!alias.equals(user.getAlias()) && !petOwner.isProfileVisible()) {
            throw new IllegalArgumentException("El perfil no es visible para otros usuarios");
        }
        return mapper.convertToDTO(petOwner);
    }

    @Transactional
    public PetOwnerProfileDTO updatePersonalInfo(PetOwnerProfileUpdateDTO dto) {
        PetOwner petOwner = getCurrentPetOwner();
        System.out.println(petOwner.getSex());
        Field[] fields = dto.getClass().getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            try {
                Object value = field.get(dto);
                if (value != null) {
                    Field perInfo;
                    try {
                        perInfo = petOwner.getClass().getDeclaredField(field.getName());
                    } catch (NoSuchFieldException e) {
                        perInfo = petOwner.getClass().getSuperclass().getDeclaredField(field.getName());
                    }
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
        return mapper.convertToDTO(petOwner);
    }

    @Transactional
    public PetOwnerProfileDTO updateProfilePicture(String path) {
        PetOwner petOwner = getCurrentPetOwner();
        petOwner.setImage(path);
        repository.save(petOwner);
        return mapper.convertToDTO(petOwner);
    }

    @Transactional
    public PetOwnerProfileDTO updateVisibility(VisibilityRequestDTO dto) {
        userService.updateVisibility(dto);
        PetOwner petOwner = getCurrentPetOwner();
        return mapper.convertToDTO(petOwner);
    }

    @Transactional
    public void follow(String alias) {
        PetOwner follower = getCurrentPetOwner();
        PetOwner followed = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        if (follower.getId().equals(followed.getId())) {
            throw new IllegalArgumentException("El usuario no puede seguirse a si mismo");
        }
        boolean followExist = followersRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId());
        if (followExist) {
            throw new IllegalArgumentException("Ya sigues a este usuario");
        }
        
        Followers followers = new Followers();
        followers.setFollower(follower);
        followers.setFollowed(followed);
        followersRepository.save(followers);

        follower.setFollowed(follower.getFollowed() + 1);
        followed.setFollowers(followed.getFollowers() + 1);
        repository.save(follower);
        repository.save(followed);

    }

    @Transactional
    public void unfollow (String alias){
        PetOwner follower = getCurrentPetOwner();
        PetOwner followed = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));
        if (follower.getId().equals(followed.getId())) {
            throw new IllegalArgumentException("El usuario no puede dejar de seguirse a si mismo");
        }
        boolean followExist = followersRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId());
        if (!followExist) {
            throw new IllegalArgumentException("No sigues a este usuario");
        }

        followersRepository.deleteByFollowerIdAndFollowedId(follower.getId(), followed.getId());

        follower.setFollowed(follower.getFollowed() - 1);
        followed.setFollowers(followed.getFollowers() - 1);
        repository.save(follower);
        repository.save(followed);
    }

    @Transactional(readOnly = true)
    public List<FollowerResponseDTO> getFollowers(String alias) {
        User user = userService.getUserByAuth();
        PetOwner followed = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        if (!alias.equals(user.getAlias()) && !followed.isProfileVisible()) {
            throw new IllegalArgumentException("El perfil no es visible para otros usuarios");
        }

        List<Followers> followersEntities = followersRepository.findByFollowedId(followed.getId());
        List<PetOwner> followers = followersEntities.stream()
            .map(Followers::getFollower)
            .collect(Collectors.toList());
        return mapper.convertToDTOFollowerList(followers);
    }

    @Transactional(readOnly = true)
    public List<FollowerResponseDTO> getFollowed(String alias) {
        User user = userService.getUserByAuth();
        PetOwner follower = repository.findByAlias(alias)
            .orElseThrow(() -> new ResourceNotFoundException("El usuario no existe"));

        if (!alias.equals(user.getAlias()) && !follower.isProfileVisible()) {
            throw new IllegalArgumentException("El perfil no es visible para otros usuarios");
        }
        
        List<Followers> followedEntities = followersRepository.findByFollowerId(follower.getId());

        List<PetOwner> followed = followedEntities.stream()
            .map(Followers::getFollowed)
            .collect(Collectors.toList());

        return mapper.convertToDTOFollowerList(followed);
    }

    public PetOwner getCurrentPetOwner() {
        User user = userService.getUserByAuth();
        if (!(user instanceof PetOwner)) {
            throw new ResourceNotFoundException("El usuario no es un PetOwner");
        }
        return (PetOwner) user;
    }
}