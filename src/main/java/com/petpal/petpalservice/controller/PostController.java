package com.petpal.petpalservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.petpal.petpalservice.model.dto.CommentRequestDTO;
import com.petpal.petpalservice.model.dto.CommentResponseDTO;
import com.petpal.petpalservice.model.dto.CommentUpdateDTO;
import com.petpal.petpalservice.model.dto.PostRequestDTO;
import com.petpal.petpalservice.model.dto.PostResponseDTO;
import com.petpal.petpalservice.model.dto.PostUpdateDTO;
import com.petpal.petpalservice.service.PostService;
import com.petpal.petpalservice.service.StorageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final StorageService storageService;

    @PostMapping("/create")
    public ResponseEntity<PostResponseDTO> createPost(@Validated @RequestBody PostRequestDTO postRequestDTO) {
        PostResponseDTO post = postService.createPost(postRequestDTO);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<PostResponseDTO> updatePost(@Validated @RequestBody PostUpdateDTO postUpdateDTO) {
        PostResponseDTO post = postService.updatePost(postUpdateDTO);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<PostResponseDTO> likePost(@RequestParam Long postId) {
        PostResponseDTO response = postService.likePost(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/community/{communityName}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByCommunity(@PathVariable String communityName) {
        List<PostResponseDTO> posts = postService.getPostsByCommunity(communityName);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/user/{alias}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUser(@PathVariable String alias) {
        List<PostResponseDTO> posts = postService.getPostsByUser(alias);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<PostResponseDTO> upload(@RequestParam("file") MultipartFile file,
                                                        @RequestParam Long postId) {
        String path = storageService.store(file);
        PostResponseDTO response = postService.uploadPostMedia(path, postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/alreadyLiked")
    public ResponseEntity<Boolean> alreadyLiked(@RequestParam Long postId) {
        Boolean response = postService.isLiked(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/comment/add")
    public ResponseEntity<CommentResponseDTO> addComment(@Validated @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO response = postService.addComment(commentRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<Void> deleteComment(@RequestParam Long commentId) {
        postService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/comment/update")
    public ResponseEntity<CommentResponseDTO> updateComment(@Validated @RequestBody CommentUpdateDTO commentUpdateDTO) {
        CommentResponseDTO response = postService.updateComment(commentUpdateDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/comment/list")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@RequestParam Long postId) {
        List<CommentResponseDTO> comments = postService.getComments(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    
}
