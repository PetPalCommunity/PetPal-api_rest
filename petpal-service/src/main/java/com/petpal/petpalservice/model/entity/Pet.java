package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet_owner", nullable = false)
    private PetOwner petOwner;

    @Column(name = "name", nullable = false)
    private String petName;

    @Column(name = "species", nullable = false)
    private String petSpecies;

    @Column(name = "breed", nullable = false)
    private String petBreed;

    @Column(name = "age", nullable = false)
    private int petAge;

    @Column (name = "petSex", nullable = false)
    private String petSex;

    @Column (name="image")
    private String image;
    
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reminder> reminders;
    
}
