package se.jensen.marcel.springboot1.mapper;

import org.springframework.stereotype.Component;
import se.jensen.marcel.springboot1.DTO.UserRequestDTO;
import se.jensen.marcel.springboot1.DTO.UserResponseDTO;
import se.jensen.marcel.springboot1.model.User;

@Component
public class UserMapper {

    private static User fromDTO(UserRequestDTO dto){
        User user = new User();
        user.setProfileImagePath(dto.profileImagePath());
        user.setRole(dto.role());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setUsername(dto.username());
        user.setDisplayName(dto.displayName());
        return user;
    }

    private  static UserResponseDTO toDTO(User user){
        UserResponseDTO dto = new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getDisplayName(),
                user.getProfileImagePath()
        );

        return dto;
    }
}
