package se.jensen.marcel.springboot1.DTO;

import java.util.List;

public record UserWithPostsResponseDTO(UserResponseDTO user, List<PostResponseDTO> posts) {
}
