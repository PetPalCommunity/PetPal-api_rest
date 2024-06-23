package com.petpal.petpalservice.repository;

import com.petpal.petpalservice.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAlias(String alias);   

    Optional<User> findUserByAlias(String alias);
    
}