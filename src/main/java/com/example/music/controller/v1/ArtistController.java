package com.example.music.controller.v1;

import com.example.music.dto.ArtistRequestDTO;
import com.example.music.dto.ArtistResponseDTO;
import com.example.music.model.Artist;
import com.example.music.service.ArtistService;
import com.example.music.service.MapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/artists")
@Tag(name = "Artists", description = "Artist management APIs")
public class ArtistController {

    private final ArtistService artistService;
    private final MapperService mapperService;

    public ArtistController(ArtistService artistService, MapperService mapperService) {
        this.artistService = artistService;
        this.mapperService = mapperService;
    }

    @GetMapping
    @Operation(summary = "List artists", description = "Get all artists with pagination and optional name filter")
    public ResponseEntity<Page<ArtistResponseDTO>> getAllArtists(
            @RequestParam(required = false) String name,
            @ParameterObject @PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Artist> artistPage = artistService.findAll(name, pageable);
        Page<ArtistResponseDTO> dtoPage = artistPage.map(mapperService::toArtistResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> getArtistById(@PathVariable Long id) {
        Artist artist = artistService.findById(id);
        return ResponseEntity.ok(mapperService.toArtistResponseDTO(artist));
    }

    @PostMapping
    public ResponseEntity<ArtistResponseDTO> createArtist(@Valid @RequestBody ArtistRequestDTO artistDTO) {
        Artist createdArtist = artistService.create(artistDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdArtist.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapperService.toArtistResponseDTO(createdArtist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArtistResponseDTO> updateArtist(@PathVariable Long id, @Valid @RequestBody ArtistRequestDTO artistDTO) {
        Artist updatedArtist = artistService.update(id, artistDTO);
        return ResponseEntity.ok(mapperService.toArtistResponseDTO(updatedArtist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
