package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.PetOwner;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
public class FollowedWithFollowersDto {
    private PetOwner followed;
    private List<PetOwner> followers;
}
