package com.vblocks.gtime.component;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.vblocks.gtime.entity.Detail;
import com.vblocks.gtime.entity.Plant;
import com.vblocks.gtime.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class PlantQuery {

    private final PlantService service;

    @DgsQuery
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public List<Plant> plants() {
        return service.getPlants();
    }

    @DgsQuery
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public Plant plant(@InputArgument Long id) {
        return service.getPlant(id);
    }
}
