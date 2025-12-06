package com.vblocks.gtime.entity.plant;

import com.vblocks.gtime.entity.detail.Detail;
import com.vblocks.gtime.entity.Note;
import com.vblocks.gtime.entity.UserPlant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "plants")
@AllArgsConstructor
@NoArgsConstructor
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Works with Postgres
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String latinName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String image;


    @ManyToMany
    @JoinTable(
            name = "plant_details",
            joinColumns = @JoinColumn(name = "plant_id"),
            inverseJoinColumns = @JoinColumn(name = "detail_id")
    )
    private List<Detail> details = new ArrayList<>(0);

    @Column(nullable = false)
    private CareDifficulty careDifficulty; // EASY, MEDIUM, HARD

    @OneToMany(mappedBy = "plantType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserPlant> userPlants = new ArrayList<>();

    @OneToMany(mappedBy = "noteFor", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WateringFrequency> wateringFrequencies;

    public void addFrequency(WateringFrequency frequency) {
        wateringFrequencies.add(frequency);
        frequency.setPlant(this);
    }

    public void addDetail(Detail detail) {
        details.add(detail);
    }

    public void addDetails(List<Detail> details) {
        this.details.addAll(details);
    }

    public void addNote(Note note) {
        notes.add(note);
        note.setNoteFor(this);
    }

    // Helper method to remove a note
    public void removeNote(Note note) {
        notes.remove(note);
        note.setNoteFor(null);
    }
}
