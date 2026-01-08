package se.jensen.marcel.springboot1.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.marcel.springboot1.DTO.*;
import se.jensen.marcel.springboot1.mapper.UserMapper;
import se.jensen.marcel.springboot1.model.User;
import se.jensen.marcel.springboot1.repository.PostRepository;
import se.jensen.marcel.springboot1.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService { // OBS: Tog bort "implements UserDetailsService"

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(PostRepository postRepository, UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponseDTO> getAllUsers(){
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getDisplayName(),
                        user.getProfileImagePath()
                )).toList();
    }

    public UserResponseDTO addUser(UserRequestDTO userDto) {
        if (userRepository.existsByUsernameOrEmail(userDto.username(), userDto.email())) {
            throw new IllegalArgumentException("User med detta username eller email finns redan.");
        }

        User user = fromDto(userDto);
        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    public UserResponseDTO toDto(User user){
        return new UserResponseDTO(
                user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.getDisplayName(),
                user.getProfileImagePath());
    }

    private User fromDto(UserRequestDTO userDto) {
        User user = new User();
        user.setRole(userDto.role());
        user.setEmail(userDto.email());
        user.setUsername(userDto.username());

        // VIKTIGT: Här hashar vi lösenordet innan det sparas!
        user.setPassword(passwordEncoder.encode(userDto.password()));

        user.setDisplayName(userDto.displayName());
        user.setProfileImagePath(userDto.profileImagePath());
        return user;
    }

    public UserWithPostsResponseDTO getUserWithPosts(Long id){
        User user = userRepository.findUserWithPosts(id).
                orElseThrow( () -> new NoSuchElementException("User not found with id " + id));

        List<PostResponseDTO> posts = user.getPosts()
                .stream().map(p -> new PostResponseDTO(
                        p.getId(),
                        p.getText(),
                        p.getCreatedAt()
                )).toList();

        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDisplayName(),
                user.getProfileImagePath()
        );

        return new UserWithPostsResponseDTO(dto, posts);
    }

    public UserResponseDTO getUserByUsername(String username){
        User user = userRepository.findByUsername(username).
                orElseThrow(() -> new NoSuchElementException("Användaren finns inte"));

        UserResponseDTO foundUser = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDisplayName(),
                user.getProfileImagePath());

        return foundUser;

    }
}