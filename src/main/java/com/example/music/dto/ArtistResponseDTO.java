package com.example.music.dto;

import lombok.Data;
import java.util.Set;

@Data
public class ArtistResponseDTO {
    private Long id;
    private String name;
    private Set<AlbumSummaryDTO> albums;
}
