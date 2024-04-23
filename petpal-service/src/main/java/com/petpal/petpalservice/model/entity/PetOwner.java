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
    private String owner_name;
    @Column(name = "sex", nullable = false)
    private String owner_sex;
    @Column(name = "age", nullable = false)
    private int owner_age;
    @Column(name = "phone_numb", nullable = false, unique = true)
    private int owner_phone;
    @Column(name = "email", nullable = false, unique = true)
    private String owner_email;
    @Column(name = "password", nullable = false)
    private String owner_password;
    @Column(name = "image")
    private String owner_image;
    @Column(name = "reputation")
    private float owner_reputation;
    @Column(name = "followers")
    private int followers;
    @Column(name = "followed")
    private int followed;
}
