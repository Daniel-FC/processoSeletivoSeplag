package com.example.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArtistRequestDTO {
    @NotBlank(message = "Artist name cannot be blank")
    private String name;
}
