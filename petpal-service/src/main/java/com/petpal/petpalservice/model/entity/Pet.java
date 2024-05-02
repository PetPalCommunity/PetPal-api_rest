package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pet")

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPet;
    @ManyToOne
    @JoinColumn(name = "id_pet_owner", nullable = false)
    private PetOwner petOwner;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "species", nullable = false)
    private String species;
    @Column(name = "breed", nullable = false)
    private String breed;
    @Column(name = "age", nullable = false)
    private int age;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "image", nullable = false)
    private String image;

}
