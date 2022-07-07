package com.proj2.proj2.repository;

import com.proj2.proj2.model.UserJwtToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJwtTokenRepository extends JpaRepository<UserJwtToken, Long> {
    
    UserJwtToken findByUserId(Long userId);
}
