package com.petpal.petpalservice.model.entities;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "communities")
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "likes")
    private Long likes;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "Community_Tag",
        joinColumns = {@JoinColumn(name = "id")}
    )
    @Column(name = "tags")
    private List<String> tags;
}
