package com.example.music.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
public class AlbumRequestDTO {
    @NotBlank(message = "Album title cannot be blank")
    private String title;
    private Integer releaseYear;
    private Set<Long> artistIds;
}
