package com.vblocks.gtime.entity.plant;

import com.vblocks.gtime.entity.GrowthStage;
import jakarta.persistence.*;
import lombok.*;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WateringFrequency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float length;

    @Enumerated(EnumType.STRING)
    private TimeUnit timeUnit;

    @Enumerated(EnumType.STRING)
    private GrowthStage growthStage;

    @ManyToOne(fetch = FetchType.LAZY) // Many frequencies belong to one Plant
    @JoinColumn(name = "plant_id") // Foreign key column
    private Plant plant;
}
