package com.petpal.petpalservice.mapper;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.petpal.petpalservice.model.dto.CommentResponseDTO;
import com.petpal.petpalservice.model.dto.PostRequestDTO;
import com.petpal.petpalservice.model.dto.PostResponseDTO;
import com.petpal.petpalservice.model.entity.Comment;
import com.petpal.petpalservice.model.entity.Post;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PostMapper {
    private final ModelMapper modelMapper;

    public Post convertToEntity(PostRequestDTO postRequestDTO){
        return modelMapper.map(postRequestDTO, Post.class);
    }

    public PostResponseDTO convertToDTO(Post post){
        PostResponseDTO postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
        postResponseDTO.setAlias(post.getUser().getAlias());
        return  postResponseDTO;
    }

    public List<PostResponseDTO> convertToDTOList(List<Post> posts){
        return posts.stream()
                .map(this::convertToDTO)
                .toList();
    }

    public CommentResponseDTO convertToCommentDTO(Comment comment){
        CommentResponseDTO commentResponseDTO = modelMapper.map(comment, CommentResponseDTO.class);
        commentResponseDTO.setAlias(comment.getUser().getAlias());
        return commentResponseDTO;
    }

    public List<CommentResponseDTO> convertToCommentDTOList(List<Comment> comments){
        return comments.stream()
                .map(this::convertToCommentDTO)
                .toList();
    }
    
}
