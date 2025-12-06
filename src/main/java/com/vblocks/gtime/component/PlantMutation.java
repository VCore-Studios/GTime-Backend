package com.vblocks.gtime.component;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.vblocks.gtime.dto.PlantInput;
import com.vblocks.gtime.entity.Plant;
import com.vblocks.gtime.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@DgsComponent
@RequiredArgsConstructor
public class PlantMutation {

    private final PlantService service;

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Plant createPlant(@InputArgument PlantInput input) {
        return service.createPlant(input);
    }

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Plant editPlant(@InputArgument Long plantId, @InputArgument PlantInput plantDetails) {
        return service.editPlantByName(plantId, plantDetails);
    }

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Boolean deletePlant(@InputArgument Long plantId) {
        return service.deletePlant(plantId);
    }
}
