package com.petpal.petpalservice.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends User{
    @Column(name = "speciality")
    private String speciality;
    
    @Column(name = "license_number")
    private String license_number;

    @Column(name = "reputation")
    private BigDecimal reputation;

    @Column(name = "location")
    private String location;
}
