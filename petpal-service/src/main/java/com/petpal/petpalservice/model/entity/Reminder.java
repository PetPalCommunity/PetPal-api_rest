package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reminders")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reminder")
    private int id;

    @Column(name = "reminder_name", nullable = false)
    private String reminderName;
    @Column(name = "reminder_description", nullable = false)
    private String reminderDescription;
    @Column(name = "reminder_date", nullable = false)
    private LocalDate nextReminderDate;
    @Column(name = "reminder_time", nullable = false)
    private LocalTime reminderTime;
    
    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "reminder_days", joinColumns = @JoinColumn(name = "id_reminder"))
    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> days; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pet")
    private Pet pet;
}
