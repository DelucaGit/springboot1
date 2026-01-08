package se.jensen.marcel.springboot1.service;

import org.springframework.stereotype.Service;
import se.jensen.marcel.springboot1.DTO.PostRequestDTO;
import se.jensen.marcel.springboot1.DTO.PostResponseDTO;
import se.jensen.marcel.springboot1.model.Post;
import se.jensen.marcel.springboot1.model.User;
import se.jensen.marcel.springboot1.repository.PostRepository;
import se.jensen.marcel.springboot1.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // Skapa inlägg (Används av UserController)
    public PostResponseDTO createPost(Long userId, PostRequestDTO postDTO){
        Post post = new Post();
        post.setText(postDTO.text());
        post.setCreatedAt(LocalDateTime.now());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id " + userId));
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return new PostResponseDTO(savedPost.getId(), savedPost.getText(), savedPost.getCreatedAt());
    }

    // Uppdatera inlägg
    public PostResponseDTO updatePost(Long postId, PostRequestDTO postDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id " + postId));

        post.setText(postDTO.text());
        // Vi kan välja att uppdatera timestamp eller behålla originalet

        Post updatedPost = postRepository.save(post);
        return new PostResponseDTO(updatedPost.getId(), updatedPost.getText(), updatedPost.getCreatedAt());
    }

    // Radera inlägg
    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("Post not found with id " + postId);
        }
        postRepository.deleteById(postId);
    }
}