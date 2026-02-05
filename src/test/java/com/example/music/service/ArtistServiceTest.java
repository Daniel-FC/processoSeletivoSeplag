package com.example.music.service;

import com.example.music.model.Artist;
import com.example.music.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    @Test
    void findAll_ShouldReturnPageOfArtists() {
        Pageable pageable = PageRequest.of(0, 10);
        Artist artist = new Artist();
        artist.setName("Test Artist");
        Page<Artist> artistPage = new PageImpl<>(Collections.singletonList(artist));

        when(artistRepository.findAll(pageable)).thenReturn(artistPage);

        Page<Artist> result = artistService.findAll(null, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(artistRepository, times(1)).findAll(pageable);
    }

    @Test
    void findById_ShouldReturnArtist_WhenExists() {
        Long id = 1L;
        Artist artist = new Artist();
        artist.setId(id);
        artist.setName("Test Artist");

        when(artistRepository.findById(id)).thenReturn(Optional.of(artist));

        Artist result = artistService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        Long id = 1L;
        when(artistRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> artistService.findById(id));
    }

    @Test
    void save_ShouldReturnSavedArtist() {
        Artist artist = new Artist();
        artist.setName("New Artist");

        when(artistRepository.save(any(Artist.class))).thenReturn(artist);

        Artist result = artistService.save(artist);

        assertNotNull(result);
        assertEquals("New Artist", result.getName());
    }
}
