package com.vblocks.gtime.component.userPlant;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.vblocks.gtime.entity.UserPlant;
import com.vblocks.gtime.service.UserPlantService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import static com.vblocks.gtime.util.AuthUtil.getAuthenticatedUsername;

@DgsComponent
@AllArgsConstructor
public class UserPlantQuery {

    private UserPlantService userPlantService;

    @DgsQuery
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public List<UserPlant> myPlants(DgsDataFetchingEnvironment dfe) {
        String username = getAuthenticatedUsername(dfe);
        return userPlantService.findAllByUserUsername(username);
    }
}
