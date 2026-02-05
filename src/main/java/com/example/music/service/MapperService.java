package com.example.music.service;

import com.example.music.dto.*;
import com.example.music.model.Album;
import com.example.music.model.Artist;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MapperService {

    private final ModelMapper modelMapper;
    private final StorageService storageService;

    public MapperService(ModelMapper modelMapper, StorageService storageService) {
        this.modelMapper = modelMapper;
        this.storageService = storageService;
    }

    public ArtistResponseDTO toArtistResponseDTO(Artist artist) {
        ArtistResponseDTO dto = modelMapper.map(artist, ArtistResponseDTO.class);
        if (artist.getAlbums() != null) {
            dto.setAlbums(artist.getAlbums().stream()
                    .map(album -> modelMapper.map(album, AlbumSummaryDTO.class))
                    .collect(Collectors.toSet()));
        }
        return dto;
    }

    public AlbumResponseDTO toAlbumResponseDTO(Album album) {
        AlbumResponseDTO dto = modelMapper.map(album, AlbumResponseDTO.class);
        if (album.getArtists() != null) {
            dto.setArtists(album.getArtists().stream()
                    .map(artist -> modelMapper.map(artist, ArtistSummaryDTO.class))
                    .collect(Collectors.toSet()));
        }
        if (album.getImages() != null) {
            dto.setImages(album.getImages().stream()
                    .map(image -> {
                        AlbumImageDTO imageDTO = modelMapper.map(image, AlbumImageDTO.class);
                        imageDTO.setUrl(storageService.getPresignedUrl(image.getFileName()));
                        return imageDTO;
                    })
                    .collect(Collectors.toSet()));
        }
        return dto;
    }
}
