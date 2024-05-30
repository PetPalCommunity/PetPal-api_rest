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
@Table(name = "document")

public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDocument;
    @ManyToOne
    @JoinColumn(name = "id_record", nullable = false)
    private MedicalRecord record;
    @Column(name = "link", nullable = false)
    private String link;
}