package se.jensen.marcel.springboot1.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.marcel.springboot1.DTO.PostRequestDTO;
import se.jensen.marcel.springboot1.DTO.PostResponseDTO;
import se.jensen.marcel.springboot1.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // Injicera PostService istället för UserRepository direkt
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // UPDATE POST (Använder databas-ID istället för list-index)
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostRequestDTO dto)
    {
        PostResponseDTO response = postService.updatePost(id, dto);
        return ResponseEntity.ok(response);
    }

    // DELETE POST
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}