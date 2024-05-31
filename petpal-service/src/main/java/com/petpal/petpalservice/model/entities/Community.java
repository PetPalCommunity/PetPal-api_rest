package com.petpal.petpalservice.model.entities;
import java.time.LocalDate;
import java.util.List;
// import java.util.List;

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
  @Column(name = "community_id", nullable = false)
  private Long id;
  @Column(name = "name", unique = true)
  private String name;
  @Column(name = "description")
  private String description;
  @Column(name = "count_followers")
  private Long countFollowers = 0L;
  @Column(name = "tags")
  List<String> tags;
  @Column(name="creation_date")
  LocalDate creationDate;
  @OneToMany
  @JoinColumn(name = "community_id")
  private List<CommunityUser> communityUsers;

  public List<CommunityUser> getCommunityUsers() {
	return communityUsers;
}
public void setCommunityUsers(List<CommunityUser> communityUsers) {
	this.communityUsers = communityUsers;
}
public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Long getCountFollowers() {
    return countFollowers;
  }
  public void setCountFollowers(Long countFollowers) {
    this.countFollowers = countFollowers;
  }
  public List<String> getTags() {
    return tags;
  }
  public void setTags(List<String> tags) {
    this.tags = tags;
  }
  public LocalDate getCreationDate() {
    return creationDate;
  }
  public void setCreationDate(LocalDate creationDate) {
    this.creationDate = creationDate;
  }

}
