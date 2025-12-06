package com.vblocks.gtime.service;

import com.vblocks.gtime.dto.plant.PlantInput;
import com.vblocks.gtime.entity.detail.Detail;
import com.vblocks.gtime.entity.plant.Plant;
import com.vblocks.gtime.repository.DetailRepository;
import com.vblocks.gtime.repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepo;
    private final DetailRepository detailRepo;

    public Plant createPlant(PlantInput plantInput) {
        Plant plant = new Plant();
        plant.setName(plantInput.getName());
        plant.setLatinName(plantInput.getLatinName());
        plant.setDescription(plantInput.getDescription());
        plant.setImage(plantInput.getImage());
        plant.setCareDifficulty(plantInput.getCareDifficulty());
        plant.setWateringFrequencies(plantInput.getWateringFrequencies());
        for (Long detailName : plantInput.getDetails()) {
            Optional<Detail> detail = detailRepo.findById(detailName);
            if (detail.isEmpty()) continue;
            plant.addDetail(detail.get());
        }
        return plantRepo.save(plant);
    }

    public Plant editPlantByName(Long id, PlantInput plantInput) {
        Plant plant = plantRepo.findById(id).orElse(null);
        if (plant == null) return null;
        if (!plantInput.getName().isEmpty()) {
            plant.setName(plantInput.getName());
        }
        if (!plantInput.getDetails().isEmpty()) {
            List<Detail> details = new ArrayList<>(0);
            for (Long detailName : plantInput.getDetails()) {
                Optional<Detail> detail = detailRepo.findById(detailName);
                if (detail.isEmpty()) continue;
                details.add(detail.get());
            }
            plant.addDetails(details);
        }
        return plantRepo.save(plant);
    }

    public Boolean deletePlant(Long id) {
        return plantRepo.deletePlantById(id);
    }

    public List<Plant> getPlants() {
        return plantRepo.findAll();
    }

    public Plant getPlant(Long id) {
        return plantRepo.findById(id).orElse(null);
    }
}
