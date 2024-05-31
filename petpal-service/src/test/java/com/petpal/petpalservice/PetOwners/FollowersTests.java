package com.petpal.petpalservice.PetOwners;

import com.petpal.petpalservice.controller.PetOwnerController;
import com.petpal.petpalservice.model.dto.FollowedWithFollowersDto;
import com.petpal.petpalservice.model.entity.Followers;
import com.petpal.petpalservice.model.entity.PetOwner;
import com.petpal.petpalservice.repository.FollowersRepository;
import com.petpal.petpalservice.exception.UserNotFoundException;
import com.petpal.petpalservice.service.PetOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FollowersTests {

    @InjectMocks
    private PetOwnerController controller;

    @Mock
    private PetOwnerService service;

    @Mock
    private FollowersRepository followersRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFollow() {
        PetOwner follower = new PetOwner();
        follower.setId(1);
        PetOwner followed = new PetOwner();
        followed.setId(2);

        when(service.getPetOwner(1)).thenReturn(follower);
        when(service.getPetOwner(2)).thenReturn(followed);

        ResponseEntity<Followers> response = controller.follow(1, 2);

        verify(service, times(1)).incrementFollowersCount(1);
        verify(service, times(1)).incrementFollowedCount(2);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUnfollow() {
        PetOwner follower = new PetOwner();
        follower.setId(1);
        follower.setOwnerFollowed(1);
        PetOwner followed = new PetOwner();
        followed.setId(2);
        followed.setOwnerFollowers(1);

        when(service.getPetOwner(1)).thenReturn(follower);
        when(service.getPetOwner(2)).thenReturn(followed);

        ResponseEntity<Void> response = controller.unfollow(1, 2);

        verify(service, times(1)).decrementFollowersCount(1);
        verify(service, times(1)).decrementFollowedCount(2);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetFollowers() {
        PetOwner followed = new PetOwner();
        followed.setId(1);

        Followers followers = new Followers();
        followers.setFollowed(followed);
        followers.setFollower(new PetOwner());

        when(service.getPetOwner(1)).thenReturn(followed);
        when(followersRepository.findByFollowedId(1)).thenReturn(Arrays.asList(followers));

        ResponseEntity<FollowedWithFollowersDto> response = controller.getFollowers(1);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        List<PetOwner> followersList = response.getBody().getFollowers();
        assertEquals(1, followersList.size());
    }

    @Test
    public void testFollow_NonExistentUser() {
        when(service.getPetOwner(1)).thenThrow(new UserNotFoundException("PetOwner with id 1 not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            controller.follow(1, 2);
        });

        assertEquals("PetOwner with id 1 not found", exception.getMessage());
    }


    @Test
    public void testFollow_SelfFollow() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.follow(1, 1);
        });

        assertEquals("User cannot follow themselves", exception.getMessage());
    }

    @Test
    public void testUnfollow_NonFollowingUsers() {
        PetOwner follower = new PetOwner();
        follower.setId(1);
        follower.setOwnerFollowed(0);
        PetOwner followed = new PetOwner();
        followed.setId(2);
        followed.setOwnerFollowers(0);

        when(service.getPetOwner(1)).thenReturn(follower);
        when(service.getPetOwner(2)).thenReturn(followed);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            controller.unfollow(1, 2);
        });

        assertEquals("Invalid IDs", exception.getMessage());
    }

    @Test
    public void testGetFollowers_NonExistentUser() {
        when(service.getPetOwner(1)).thenThrow(new UserNotFoundException("PetOwner with id 1 not found"));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            controller.getFollowers(1);
        });

        assertEquals("PetOwner with id 1 not found", exception.getMessage());
    }
}
