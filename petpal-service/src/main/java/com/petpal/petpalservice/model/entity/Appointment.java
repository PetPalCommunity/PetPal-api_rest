package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Appointment")

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAppointment")
    private int id;
    @Column(name = "idVet", nullable = false)
    private int id_vet;
    @Column(name = "idPet", nullable = false)
    private int id_pet;
    @Column(name = "idPayment", nullable = false)
    private int id_payment;
    @Column(name = "date", nullable = false, unique = true)
    private Date date;
    @Column(name = "time", nullable = false, unique = true)
    private Time time;
    @Column(name = "reason", nullable = false)
    private String reason;
}
