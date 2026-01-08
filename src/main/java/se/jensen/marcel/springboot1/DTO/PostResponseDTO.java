package se.jensen.marcel.springboot1.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        String text,
        LocalDateTime createdAt){}
