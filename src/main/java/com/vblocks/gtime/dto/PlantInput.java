package com.vblocks.gtime.dto;

import com.vblocks.gtime.entity.CareDifficulty;
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
    private WateringFrequency wateringFrequency;
}
