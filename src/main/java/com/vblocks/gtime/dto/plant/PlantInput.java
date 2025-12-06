package com.vblocks.gtime.dto.plant;

import com.vblocks.gtime.entity.plant.CareDifficulty;
import com.vblocks.gtime.entity.plant.WateringFrequency;
import lombok.Data;

import java.util.List;

@Data
public class PlantInput {
    private String name;
    private String latinName;
    private String description;
    private String image;
    private List<Long> details;
    private CareDifficulty careDifficulty;
    private List<WateringFrequency> wateringFrequencies;
}
