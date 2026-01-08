package se.jensen.marcel.springboot1.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(
        @NotBlank(message = "Text får inte vara tomt.")
        @Size(min = 3, max = 200)
        String text){}
