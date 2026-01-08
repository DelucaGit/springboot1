package se.jensen.marcel.springboot1.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import se.jensen.marcel.springboot1.DTO.*;
import se.jensen.marcel.springboot1.model.User;
import se.jensen.marcel.springboot1.service.PostService;
import se.jensen.marcel.springboot1.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final PostService postService;
    private final UserService userService;

    public UserController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        // Tog bort den hårdkodade "userService.registerUser..." här för att undvika krasch
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO dto) {
        // Anropar servicen som hashar lösenordet
        UserResponseDTO response = userService.addUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        // För enkelhetens skull, försök använda servicen här också i framtiden,
        // men detta fungerar för nu om du vill hämta via repository (via service är snyggare)
        return ResponseEntity.ok(userService.getAllUsers().stream()
                .filter(u -> u.id().equals(id))
                .findFirst()
                .orElse(null));
        // OBS: Bättre att implementera getUserById i UserService,
        // men jag lämnar din logik intakt fast via servicen.
    }

    @GetMapping("/{id}/with-posts")
    public ResponseEntity<UserWithPostsResponseDTO> getUserWithPosts(@PathVariable Long id){
        UserWithPostsResponseDTO response = userService.getUserWithPosts(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER,ADMIN')")
    @GetMapping("/me")
    public UserResponseDTO getMe(Authentication authentication){
        String username = authentication.getName();
        return userService.getUserByUsername(username);
    }

    @PostMapping("/{userId}/posts")
    public ResponseEntity<PostResponseDTO> createPostForUser(
            @PathVariable Long userId,
            @Valid @RequestBody PostRequestDTO request){

        PostResponseDTO response = postService.createPost(userId,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}