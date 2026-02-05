package com.example.music.service;

import com.example.music.model.Regional;
import com.example.music.repository.RegionalRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RegionalSyncService {

    private final RegionalRepository regionalRepository;
    private final RestTemplate restTemplate;

    public RegionalSyncService(RegionalRepository regionalRepository) {
        this.regionalRepository = regionalRepository;
        this.restTemplate = new RestTemplate();
    }

    @Scheduled(fixedRate = 300000) // Sync every 5 minutes
    @Transactional
    public void syncRegionals() {
        String url = "https://integrador-argus-api.geia.vip/v1/regionais";
        RegionalDTO[] externalRegionalsArray = restTemplate.getForObject(url, RegionalDTO[].class);
        
        if (externalRegionalsArray == null) return;

        List<RegionalDTO> externalRegionals = Arrays.asList(externalRegionalsArray);
        
        // Map of External ID -> Regional Entity (Active ones)
        Map<Integer, Regional> activeRegionalsMap = regionalRepository.findByAtivoTrue().stream()
                .collect(Collectors.toMap(Regional::getId, Function.identity()));

        for (RegionalDTO dto : externalRegionals) {
            if (activeRegionalsMap.containsKey(dto.getId())) {
                Regional existing = activeRegionalsMap.get(dto.getId());
                
                // Check if name changed
                if (!existing.getNome().equals(dto.getNome())) {
                    // Inactivate old
                    existing.setAtivo(false);
                    regionalRepository.save(existing);
                    
                    // Create new
                    Regional newRegional = new Regional();
                    newRegional.setId(dto.getId());
                    newRegional.setNome(dto.getNome());
                    newRegional.setAtivo(true);
                    regionalRepository.save(newRegional);
                }
                // Remove from map to track what's missing later
                activeRegionalsMap.remove(dto.getId());
            } else {
                // New record
                Regional newRegional = new Regional();
                newRegional.setId(dto.getId());
                newRegional.setNome(dto.getNome());
                newRegional.setAtivo(true);
                regionalRepository.save(newRegional);
            }
        }

        // Inactivate missing
        for (Regional remaining : activeRegionalsMap.values()) {
            remaining.setAtivo(false);
            regionalRepository.save(remaining);
        }
    }

    // Inner DTO for external API
    private static class RegionalDTO {
        private Integer id;
        private String nome;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }
}
