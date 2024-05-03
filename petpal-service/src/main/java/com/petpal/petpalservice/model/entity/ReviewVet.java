package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviewVet")
public class ReviewVet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReview;
    @ManyToOne
    @JoinColumn(name = "id_vet", nullable = false)
    private Vet vet;
    @ManyToOne
    @JoinColumn(name = "id_pet_owner", nullable = false)
    private PetOwner petOwner;
    @Column(name = "stars")
    private BigDecimal stars;
    @Column(name = "comment")
    private String comment;
}