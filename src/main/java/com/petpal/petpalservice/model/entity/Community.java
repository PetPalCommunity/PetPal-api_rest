package com.petpal.petpalservice.model.entity;

import java.time.LocalDate;
import java.util.Set;

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

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "owner_alias", nullable = false)
  private String ownerAlias;

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "count_member")
  private Long countMembers = 0L;

  @Column(name = "tags")
  private String tags;

  @Column(name="creation_date", nullable = false)
  private LocalDate creationDate;
  
  @ManyToMany
  @JoinTable(
      name = "user_community",
      joinColumns = @JoinColumn(name = "community_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<User> users;

}
