package com.vblocks.gtime.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPlantInput {
    private Long plantType;
    private String name;
}
