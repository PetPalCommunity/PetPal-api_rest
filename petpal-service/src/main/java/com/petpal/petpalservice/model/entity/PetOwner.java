package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pet_owners")
public class PetOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pet_owner")
    private int id;
    @Column(name = "name", nullable = false)
    private String ownerName;
    @Column(name = "sex", nullable = false)
    private String ownerSex;
    @Column(name = "age", nullable = false)
    private int ownerAge;
    @Column(name = "phone_numb", nullable = false, unique = true)
    private int ownerPhone;
    @Column(name = "email", nullable = false, unique = true)
    private String ownerEmail;
    @Column(name = "password", nullable = false)
    private String ownerPassword;
    @Column(name = "image")
    private String ownerImage;
    @Column(name = "reputation")
    private float ownerReputation;
    @Column(name = "followers")
    private int ownerFollowers;
    @Column(name = "followed")
    private int ownerFollowed;
}
