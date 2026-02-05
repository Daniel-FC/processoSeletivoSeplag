package com.example.music.controller.v1;

import com.example.music.model.Regional;
import com.example.music.repository.RegionalRepository;
import com.example.music.service.RegionalSyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regionals")
@Tag(name = "Regionals", description = "Regional management APIs")
public class RegionalController {

    private final RegionalRepository regionalRepository;
    private final RegionalSyncService regionalSyncService;

    public RegionalController(RegionalRepository regionalRepository, RegionalSyncService regionalSyncService) {
        this.regionalRepository = regionalRepository;
        this.regionalSyncService = regionalSyncService;
    }

    @GetMapping
    public ResponseEntity<List<Regional>> getAllRegionals() {
        return ResponseEntity.ok(regionalRepository.findAll());
    }

    @PostMapping("/sync")
    @Operation(summary = "Force Sync", description = "Manually trigger synchronization with external API")
    public ResponseEntity<Void> forceSync() {
        regionalSyncService.syncRegionals();
        return ResponseEntity.ok().build();
    }
}
