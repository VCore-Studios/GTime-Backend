package com.vblocks.gtime.component.detail;

import com.netflix.graphql.dgs.DgsComponent;
import com.vblocks.gtime.service.DetailService;
import lombok.RequiredArgsConstructor;

@DgsComponent
@RequiredArgsConstructor
public class DetailQuery {

    private final DetailService service;

}
