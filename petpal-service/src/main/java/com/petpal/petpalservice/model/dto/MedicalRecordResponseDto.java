package com.petpal.petpalservice.model.dto;

import com.petpal.petpalservice.model.entity.Pet;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

public class MedicalRecordResponseDto {
    private int idRecord;
    private int id_pet;
    private String kind;
    private String description;
    private Date date;
}
