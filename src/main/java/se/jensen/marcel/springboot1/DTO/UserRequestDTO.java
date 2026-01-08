package se.jensen.marcel.springboot1.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record UserRequestDTO(
        String username,
        String password,
        String email,
        String role,
        String displayName,
        String profileImagePath
        ) {

}
