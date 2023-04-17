package com.wcd.clubservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {
    Optional<ClubEntity> findById(Long clubId);
}
