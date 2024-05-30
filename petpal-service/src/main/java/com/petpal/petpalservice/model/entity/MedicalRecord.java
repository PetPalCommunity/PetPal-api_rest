package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ManyToAny;

import java.sql.Date;
import java.sql.Time;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "medical_record")

public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRecord;
    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
    private Pet pet;
    @Column(name = "kind", nullable = false)
    private String kind;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "date", nullable = false)
    private Date date;
}