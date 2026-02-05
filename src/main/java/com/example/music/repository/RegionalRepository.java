package com.example.music.repository;

import com.example.music.model.Regional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionalRepository extends JpaRepository<Regional, Long> {
    List<Regional> findByAtivoTrue();
}
