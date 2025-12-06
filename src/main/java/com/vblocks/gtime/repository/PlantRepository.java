package com.vblocks.gtime.repository;

import com.vblocks.gtime.entity.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    Boolean deletePlantById(Long id);
}
