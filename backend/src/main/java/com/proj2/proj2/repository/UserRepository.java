package com.proj2.proj2.repository;

import java.util.List;

import com.proj2.proj2.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByEmail(String username);
    User findByUsername(String username);
    List<User> findAllByIdIsNot(Long id);
}
