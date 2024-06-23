package com.petpal.petpalservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.petpal.petpalservice.model.enums.Role;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public class User implements UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "alias",nullable = false,unique = true)
    private String alias;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "sex")
    private String sex;

    @Column(name = "image")
    private String image;

    @Column(name = "age")
    private int age;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "created_at",nullable = false)
    private LocalDate createdAt;

    // Visibility
    @Column(name = "profile_visible",nullable = false)
    private boolean profileVisible = true;

    @Column(name = "post_visible",nullable = false)
    private boolean postVisible = true;

    @Column(name = "pet_visible",   nullable = false)
    private boolean petVisible = true;


    @Column(name = "account_non_expired",nullable = false)
    private boolean accountNonExpired;

    @Column(name = "account_non_locked",nullable = false)
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired",nullable = false)
    private boolean credentialsNonExpired;

    @Column(name = "enabled",nullable = false)
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    
    @ManyToMany(mappedBy = "users")
    private Set<Community> communities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
