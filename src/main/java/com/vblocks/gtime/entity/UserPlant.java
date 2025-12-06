package com.vblocks.gtime.entity;

import com.vblocks.gtime.entity.plant.Plant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "user_plants")
@AllArgsConstructor
@NoArgsConstructor
public class UserPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_type_id", nullable = false)
    private Plant plantType;

    @Column(name = "last_watered")
    private LocalDate lastWatered;

    @Column(name = "last_fertilized")
    private LocalDate lastFertilized;

    private String latestImage;

    @Enumerated(EnumType.STRING)
    private GrowthStage growthStage;

}
