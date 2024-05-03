package com.petpal.petpalservice.model.entities;
import java.util.HashSet;
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
    @Column(name = "community_id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "count_followers")
    private Long countFollowers;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
        name = "Community_Tag",
        joinColumns = {@JoinColumn(name = "community_id")},
        inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "community")
    private Set<CommunityUser> communityUsers;

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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<CommunityUser> getCommunityUsers() {
		return communityUsers;
	}

	public void setCommunityUsers(Set<CommunityUser> communityUsers) {
		this.communityUsers = communityUsers;
	}


}
