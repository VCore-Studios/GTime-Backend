package com.vblocks.gtime.component.detail;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.vblocks.gtime.dto.detail.DetailInput;
import com.vblocks.gtime.entity.detail.Detail;
import com.vblocks.gtime.service.DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;

@DgsComponent
@RequiredArgsConstructor
public class DetailMutation {

    private final DetailService service;

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Detail createDetail(@InputArgument DetailInput input) {
        return service.createDetail(input);
    }

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Detail editDetail(@InputArgument Long detailId, @InputArgument DetailInput detail) {
        return service.editDetail(detailId, detail);
    }

    @DgsMutation
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Boolean deleteDetail(@InputArgument Long detailId) {
        return service.deleteDetail(detailId);
    }
}
