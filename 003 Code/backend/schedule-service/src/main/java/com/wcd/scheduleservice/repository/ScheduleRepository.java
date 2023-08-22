package com.wcd.scheduleservice.repository;

import com.wcd.scheduleservice.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query(
            "SELECT s FROM Schedule s WHERE s.clubId = :clubId AND (s.start BETWEEN :start AND :end OR s.end BETWEEN :start AND :end)"
    )
    List<Schedule> findByClubIdEqualsAndStartOrEndBetween(Long clubId, LocalDateTime start, LocalDateTime end);
}
