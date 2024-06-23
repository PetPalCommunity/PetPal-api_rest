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
@Table(name = "reviewVets")
public class ReviewVet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReview;

    @ManyToOne
    @JoinColumn(name = "id_vet", nullable = false)
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "id_pet_owner", nullable = false)
    private PetOwner petOwner;

    @Column(name = "stars", nullable = false)
    private BigDecimal stars;

    @Column(name = "comment", nullable = false, length = 500)
    private String comment;

}