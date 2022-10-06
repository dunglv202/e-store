package com.example.shopdemo.repository;

import com.example.shopdemo.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(String name);
    List<Authority> findAllByManagedByContaining(Authority authority);
}
