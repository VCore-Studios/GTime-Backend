package com.vblocks.gtime.dto;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.concurrent.TimeUnit;

@Data
@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class WateringFrequency {
    private Long length;
    private TimeUnit timeUnit;
}
