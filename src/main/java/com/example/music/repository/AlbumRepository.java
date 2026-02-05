package com.example.music.repository;

import com.example.music.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    
    @Query("SELECT a FROM Album a WHERE SIZE(a.artists) > 0")
    Page<Album> findAlbumsWithArtists(Pageable pageable);
}
