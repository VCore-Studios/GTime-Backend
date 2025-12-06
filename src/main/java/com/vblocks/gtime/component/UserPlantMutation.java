package com.vblocks.gtime.component;

import com.netflix.graphql.dgs.*;
import com.vblocks.gtime.dto.UserPlantInput;
import com.vblocks.gtime.entity.UserPlant;
import com.vblocks.gtime.service.UserPlantService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

import static com.vblocks.gtime.util.AuthUtil.getAuthenticatedUsername;

@DgsComponent
@AllArgsConstructor
public class UserPlantMutation {

    private UserPlantService userPlantService;

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN)")
    public UserPlant createUserPlant(@InputArgument UserPlantInput userPlantInput, DgsDataFetchingEnvironment dfe) {
        String username = getAuthenticatedUsername(dfe);
        return userPlantService.createUserPlant(userPlantInput, username);
    }
}
