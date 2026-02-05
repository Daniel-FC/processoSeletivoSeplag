package com.example.music.controller.v1;

import com.example.music.dto.AlbumRequestDTO;
import com.example.music.dto.AlbumResponseDTO;
import com.example.music.model.Album;
import com.example.music.service.AlbumService;
import com.example.music.service.MapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/albums")
@Tag(name = "Albums", description = "Album management APIs")
public class AlbumController {

    private final AlbumService albumService;
    private final MapperService mapperService;

    public AlbumController(AlbumService albumService, MapperService mapperService) {
        this.albumService = albumService;
        this.mapperService = mapperService;
    }

    @GetMapping
    @Operation(summary = "List albums", description = "Get all albums with pagination")
    public ResponseEntity<Page<AlbumResponseDTO>> getAllAlbums(
            @RequestParam(required = false, defaultValue = "false") boolean withArtistsOnly,
            @ParameterObject @PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Album> albumPage = withArtistsOnly ? albumService.findAlbumsWithArtists(pageable) : albumService.findAll(pageable);
        Page<AlbumResponseDTO> dtoPage = albumPage.map(mapperService::toAlbumResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable Long id) {
        Album album = albumService.findById(id);
        return ResponseEntity.ok(mapperService.toAlbumResponseDTO(album));
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> createAlbum(@Valid @RequestBody AlbumRequestDTO albumDTO) {
        Album createdAlbum = albumService.create(albumDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAlbum.getId())
                .toUri();
        return ResponseEntity.created(location).body(mapperService.toAlbumResponseDTO(createdAlbum));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> updateAlbum(@PathVariable Long id, @Valid @RequestBody AlbumRequestDTO albumDTO) {
        Album updatedAlbum = albumService.update(id, albumDTO);
        return ResponseEntity.ok(mapperService.toAlbumResponseDTO(updatedAlbum));
    }

    @PostMapping(value = "/{id}/cover", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload cover", description = "Upload album cover image")
    public ResponseEntity<AlbumResponseDTO> uploadCover(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Album updatedAlbum = albumService.uploadCover(id, file);
        return ResponseEntity.ok(mapperService.toAlbumResponseDTO(updatedAlbum));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
