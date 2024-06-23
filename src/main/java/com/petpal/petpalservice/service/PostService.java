package com.petpal.petpalservice.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petpal.petpalservice.exceptions.ResourceNotFoundException;
import com.petpal.petpalservice.mapper.PostMapper;
import com.petpal.petpalservice.model.dto.CommentRequestDTO;
import com.petpal.petpalservice.model.dto.CommentResponseDTO;
import com.petpal.petpalservice.model.dto.CommentUpdateDTO;
import com.petpal.petpalservice.model.dto.PostRequestDTO;
import com.petpal.petpalservice.model.dto.PostResponseDTO;
import com.petpal.petpalservice.model.dto.PostUpdateDTO;
import com.petpal.petpalservice.model.entity.Comment;
import com.petpal.petpalservice.model.entity.Community;
import com.petpal.petpalservice.model.entity.Like;
import com.petpal.petpalservice.model.entity.Post;
import com.petpal.petpalservice.model.entity.User;
import com.petpal.petpalservice.repository.CommentRepository;
import com.petpal.petpalservice.repository.CommunityRepository;
import com.petpal.petpalservice.repository.LikeRepository;
import com.petpal.petpalservice.repository.PostRepository;
import com.petpal.petpalservice.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final CommunityRepository communityRepository;
    private final LikeRepository LikeRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO){
        User user = userService.getUserByAuth();
        Community community = communityRepository.findCommunityByName(postRequestDTO.getCommunityName())
                                            .orElseThrow(() -> new ResourceNotFoundException("Esta comunidad no existe"));
        boolean isMember = communityRepository.existByUserIdAndCommunityId(user.getId(), community.getId());
        if(!isMember){
            throw new ResourceNotFoundException("No eres miembro de esta comunidad");
        }
        Post post = postMapper.convertToEntity(postRequestDTO);
        post.setCreationDate(LocalDate.now());
        post.setCreationTime(LocalTime.now());
        post.setUser(user);
        post.setCommunity(community);
        post.setLikes(0L);
        postRepository.save(post);
        return postMapper.convertToDTO(post);
    }

    @Transactional
    public PostResponseDTO updatePost(PostUpdateDTO postUpdateDTO){
        User user = userService.getUserByAuth();
        Post post = postRepository.findByUserIdAndPostId(user.getId(), postUpdateDTO.getPostId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Este post no pertenece al usuario actual"));
        post.setDescription(postUpdateDTO.getDescription());
        postRepository.save(post);
        return postMapper.convertToDTO(post);
    }

    @Transactional
    public PostResponseDTO likePost(Long postId){
        Post post = postRepository.findById(postId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userService.getUserByAuth();
        Optional<Like> like = LikeRepository.findByUserIdAndPostId(user.getId(), postId);
        if(like.isPresent()){
            post.setLikes(post.getLikes() - 1);
            LikeRepository.delete(like.get());
        }else{
            post.setLikes(post.getLikes() + 1);
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPost(post);
            LikeRepository.save(newLike);
        }
        postRepository.save(post);
        return postMapper.convertToDTO(post);
        
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Long postId){
        User user = userService.getUserByAuth();
        return LikeRepository.findByUserIdAndPostId(user.getId(), postId).isPresent();
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getPostsByCommunity(String communityName){
        Community community = communityRepository.findCommunityByName(communityName)
                                                .orElseThrow(() -> new ResourceNotFoundException("Esta comunidad no existe"));
        List<Post> posts = postRepository.findByCommunityId(community.getId());
        return postMapper.convertToDTOList(posts);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getPostsByUser(String alias){
        User currentUser = userService.getUserByAuth();
        User user = userRepository.findUserByAlias(alias)
                                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        if (!currentUser.getAlias().equals(alias) && !user.isPostVisible()) {
            throw new ResourceNotFoundException("Este usuario no permite ver sus publicaciones");
        }
        List<Post> posts = postRepository.findByUserAlias(user.getAlias());
        return postMapper.convertToDTOList(posts);
    }

    @Transactional
    public PostResponseDTO uploadPostMedia(String path, Long postId){
        User user = userService.getUserByAuth();
        Post post = postRepository.findByUserIdAndPostId(user.getId(), postId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Este post no pertenece al usuario actual"));
        post.setMedia(path);
        postRepository.save(post);
        return postMapper.convertToDTO(post);
    }

    @Transactional
    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO){
        User user = userService.getUserByAuth();
        Post post = postRepository.findById(commentRequestDTO.getPostId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        Comment comment = new Comment();
        comment.setContent(commentRequestDTO.getContent());
        comment.setDate(LocalDate.now());
        comment.setTime(LocalTime.now());
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        return postMapper.convertToCommentDTO(comment);
    }

    @Transactional
    public void deleteComment(Long commentId){
        User user = userService.getUserByAuth();
        Comment comment = commentRepository.findById(commentId)
                                        .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));
        if(!comment.getUser().getId().equals(user.getId())){
            throw new ResourceNotFoundException("No puedes borrar este comentario");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public CommentResponseDTO updateComment(CommentUpdateDTO commentUpdateDTO){
        User user = userService.getUserByAuth();
        Comment comment = commentRepository.findById(commentUpdateDTO.getCommentId())
                                            .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));
        if(!comment.getUser().getId().equals(user.getId())){
            throw new ResourceNotFoundException("No puedes editar este comentario");
        }
        comment.setContent(commentUpdateDTO.getContent());
        commentRepository.save(comment);
        return postMapper.convertToCommentDTO(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getComments(Long postId){
        Post post = postRepository.findById(postId)
                                    .orElseThrow(() -> new ResourceNotFoundException("Post no encontrado"));
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        return postMapper.convertToCommentDTOList(comments);
    }
    
}
