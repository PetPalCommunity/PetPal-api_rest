package com.petpal.petpalservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.petpal.petpalservice.model.dto.ChangePassRequestDTO;
import com.petpal.petpalservice.service.StorageService;
import com.petpal.petpalservice.service.UserService;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    private final StorageService storageService;

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Validated @RequestBody ChangePassRequestDTO changePassRequestDTO) {
        userService.changePassword(changePassRequestDTO);
        return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);
    }

    @GetMapping("/file/{filename}")
    public ResponseEntity<Resource> getResource(@PathVariable String filename) throws IOException {
        Resource resource = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());

        return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_TYPE, contentType)
        .body(resource);
    }
    
}
