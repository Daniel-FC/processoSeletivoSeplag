package com.example.music.service;

import com.example.music.dto.AlbumRequestDTO;
import com.example.music.model.Album;
import com.example.music.model.AlbumImage;
import com.example.music.model.Artist;
import com.example.music.repository.AlbumRepository;
import com.example.music.repository.ArtistRepository;
import com.example.music.websocket.AlbumNotificationPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final StorageService storageService;
    private final AlbumNotificationPublisher notificationPublisher;

    public AlbumService(AlbumRepository albumRepository, ArtistRepository artistRepository, StorageService storageService, AlbumNotificationPublisher notificationPublisher) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.storageService = storageService;
        this.notificationPublisher = notificationPublisher;
    }

    public Page<Album> findAll(Pageable pageable) {
        return albumRepository.findAll(pageable);
    }

    public Page<Album> findAlbumsWithArtists(Pageable pageable) {
        return albumRepository.findAlbumsWithArtists(pageable);
    }

    public Album findById(Long id) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Album not found with id: " + id));
        
        album.getImages().forEach(image -> {
            String presignedUrl = storageService.getPresignedUrl(image.getFileName());
            image.setUrl(presignedUrl);
        });
        
        return album;
    }

    @Transactional
    public Album create(AlbumRequestDTO albumDTO) {
        Album album = new Album();
        album.setTitle(albumDTO.getTitle());
        album.setReleaseYear(albumDTO.getReleaseYear());

        if (albumDTO.getArtistIds() != null && !albumDTO.getArtistIds().isEmpty()) {
            Set<Artist> artists = new HashSet<>(artistRepository.findAllById(albumDTO.getArtistIds()));
            album.setArtists(artists);
        }

        Album savedAlbum = albumRepository.save(album);
        notificationPublisher.notifyNewAlbum(savedAlbum);
        return savedAlbum;
    }

    @Transactional
    public Album update(Long id, AlbumRequestDTO albumDTO) {
        Album album = findById(id);
        album.setTitle(albumDTO.getTitle());
        album.setReleaseYear(albumDTO.getReleaseYear());

        if (albumDTO.getArtistIds() != null) {
            Set<Artist> artists = new HashSet<>(artistRepository.findAllById(albumDTO.getArtistIds()));
            album.setArtists(artists);
        }

        return albumRepository.save(album);
    }

    @Transactional
    public Album uploadCover(Long albumId, MultipartFile file) {
        Album album = findById(albumId);
        String fileName = storageService.uploadFile(file);
        
        AlbumImage image = new AlbumImage();
        image.setAlbum(album);
        image.setFileName(fileName);
        image.setContentType(file.getContentType());
        
        album.getImages().add(image);
        return albumRepository.save(album);
    }

    public void delete(Long id) {
        albumRepository.deleteById(id);
    }
}
