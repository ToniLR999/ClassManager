package com.tonilr.ClassManager.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tonilr.ClassManager.Model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
