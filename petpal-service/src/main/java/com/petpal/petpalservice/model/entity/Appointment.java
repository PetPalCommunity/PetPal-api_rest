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
@Table(name = "Appointment")

public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAppointment;
    @ManyToOne
    @JoinColumn(name = "idVet", nullable = false)
    private Vet vet;
    @ManyToOne
    @JoinColumn(name = "idPet", nullable = false)
    private Pet pet;
    @Column(name = "date", nullable = false, unique = true)
    private Date date;
    @Column(name = "time", nullable = false)
    private Time time;
    @Column(name = "reason", nullable = false)
    private String reason;
    @Column(name = "confirm")
    private boolean confirm;
}
