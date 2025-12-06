package com.vblocks.gtime.dto.userPlant;

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
