package com.example.music.service;

import com.example.music.dto.ArtistRequestDTO;
import com.example.music.model.Artist;
import com.example.music.repository.ArtistRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Page<Artist> findAll(String name, Pageable pageable) {
        if (name != null && !name.isEmpty()) {
            return artistRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return artistRepository.findAll(pageable);
    }

    public Artist findById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + id));
    }

    @Transactional
    public Artist create(ArtistRequestDTO artistDTO) {
        Artist artist = new Artist();
        artist.setName(artistDTO.getName());
        return artistRepository.save(artist);
    }

    @Transactional
    public Artist update(Long id, ArtistRequestDTO artistDTO) {
        Artist artist = findById(id);
        artist.setName(artistDTO.getName());
        return artistRepository.save(artist);
    }

    public void delete(Long id) {
        artistRepository.deleteById(id);
    }
}
