package com.example.music.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AlbumResponseDTO {
    private Long id;
    private String title;
    private Integer releaseYear;
    private Set<ArtistSummaryDTO> artists;
    private Set<AlbumImageDTO> images;
}
