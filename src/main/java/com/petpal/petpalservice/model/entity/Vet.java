package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vets")
public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vet")
    private int id;
    @Column(name = "name", nullable = false)
    private String vetName;
    @Column(name = "lastname", nullable = false)
    private String vetLastname;
    @Column(name = "sex", nullable = false)
    private String vetSex;
    @Column(name = "phone_numb", nullable = false)
    private int vetPhone;
    @Column(name = "speciality", nullable = false, unique = true)
    private String vetSpeciality;
    @Column(name = "reputation", nullable = false)
    private float vetReputation;
    @Column(name = "license_number", nullable = false, unique = true)
    private int vetLicenseNumber;
    @Column(name = "image", nullable = false)
    private String vetImage;
    @Column(name = "location", nullable = false)
    private String vetLocation;
    @Column(name = "email", nullable = false, unique = true)
    private String vetEmail;
    @Column(name = "password", nullable = false, unique = true)
    private String vetPassword;
}
