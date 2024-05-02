package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Vet")

public class Vet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVet;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "sex", nullable = false)
    private String sex;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "speciality", nullable = false)
    private String speciality;
    @Column(name = "reputation", nullable = false)
    private BigDecimal reputation;
    @Column(name = "license_number", nullable = false)
    private String licenseNumber;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
}