package com.wcd.clubservice.repository.club;

import com.wcd.clubservice.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubCustomRepository {
}
