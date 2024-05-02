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
    @Column(name = "id_appointment")
    private int idAppointment;
    @ManyToOne
    @JoinColumn(name = "idVet", nullable = false)
    private Vet vet;
    @OneToOne
    @JoinColumn(name = "idPayment", nullable = false)
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "idPet", nullable = false)
    private Pet idPet;
    @Column(name = "date", nullable = false, unique = true)
    private Date date;
    @Column(name = "time", nullable = false, unique = true)
    private Time time;
    @Column(name = "reason", nullable = false)
    private String reason;
}
