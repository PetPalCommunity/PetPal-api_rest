package com.petpal.petpalservice.controller;

import com.petpal.petpalservice.model.dto.PetOwnerRequestDto;
import com.petpal.petpalservice.model.dto.SignInRequestDto;
import com.petpal.petpalservice.model.dto.FollowedWithFollowersDto;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.service.PetOwnerService;
import com.petpal.petpalservice.model.entity.Followers;
import com.petpal.petpalservice.repository.FollowersRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;


import java.util.List;

@RestController
@RequestMapping("/api/petowners")
public class PetOwnerController {
    private final PetOwnerService service;
    private final FollowersRepository followersRepository;

    public PetOwnerController(PetOwnerService service, FollowersRepository followersRepository) {
        this.service = service;
        this.followersRepository = followersRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<PetOwner> registerPetOwner(@RequestBody PetOwnerRequestDto dto) {
        PetOwner created = service.createPetOwner(dto);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/signin")
    public ResponseEntity<PetOwner> signIn(@RequestBody SignInRequestDto dto) {
        PetOwner validated = service.validateSignIn(dto);
        return ResponseEntity.ok(validated);
    }

    @GetMapping
    public ResponseEntity<List<PetOwner>> getAllPetOwners() {
        List<PetOwner> petOwners = service.getAllPetOwners();
        return ResponseEntity.ok(petOwners);
    }

    @PostMapping("/follow")
    public ResponseEntity<Followers> follow(@RequestParam int followerId, @RequestParam int followedId) {
        if (followerId == followedId) {
            throw new IllegalArgumentException("User cannot follow themselves");
        }

        PetOwner follower = service.getPetOwner(followerId);
        PetOwner followed = service.getPetOwner(followedId);


        Followers existingFollowers = followersRepository.findByFollowerIdAndFollowedId(followerId, followedId);
        if (existingFollowers != null) {
            throw new IllegalArgumentException("User is already following this user");
        }

        service.incrementFollowersCount(followerId);
        service.incrementFollowedCount(followedId);

        Followers followers = new Followers();
        followers.setFollower(follower);
        followers.setFollowed(followed);
        followersRepository.save(followers);
        return ResponseEntity.ok(followers);

    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollow(@RequestParam int followerId, @RequestParam int followedId) {
        if (followerId == followedId) {
            throw new IllegalArgumentException("User cannot unfollow themselves");
        }

        PetOwner follower = service.getPetOwner(followerId);
        PetOwner followed = service.getPetOwner(followedId);

        if (follower.getOwnerFollowed() <= 0 || followed.getOwnerFollowers() <= 0) {
            throw new IllegalArgumentException("Invalid IDs");
        }

        service.decrementFollowersCount(followerId);
        service.decrementFollowedCount(followedId);

        Followers followers = followersRepository.findByFollowerIdAndFollowedId(followerId, followedId);
        if (followers != null) {
            followersRepository.delete(followers);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers")
    public ResponseEntity<FollowedWithFollowersDto> getFollowers(@RequestParam int id) {
        PetOwner followed = service.getPetOwner(id);
        List<Followers> followersEntities = followersRepository.findByFollowedId(id);

        List<PetOwner> followers = followersEntities.stream()
                .map(Followers::getFollower)
                .collect(Collectors.toList());

        FollowedWithFollowersDto dto = new FollowedWithFollowersDto();
        dto.setFollowed(followed);
        dto.setFollowers(followers);

        return ResponseEntity.ok(dto);
    }


}