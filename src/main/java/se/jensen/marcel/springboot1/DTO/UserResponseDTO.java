package se.jensen.marcel.springboot1.DTO;

public record UserResponseDTO(
                                Long id,
                                String username,
                                String email,
                                String role,
                                String displayName,
                                String profileImagePath) {
}
