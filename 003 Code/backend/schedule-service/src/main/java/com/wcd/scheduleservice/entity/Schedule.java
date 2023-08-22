package com.wcd.scheduleservice.entity;

import com.wcd.scheduleservice.dto.ResponseScheduleDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "club_id")
    private Long clubId;

    private String title;

    private String description;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @Column(name = "all_day")
    private boolean allDay;

    @Column(name = "disclosed_grade")
    private String disclosedGrade;

    @Builder
    public Schedule(Long clubId, String title, String description, LocalDateTime start, LocalDateTime end, boolean allDay, String disclosedGrade) {
        this.clubId = clubId;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
        this.disclosedGrade = disclosedGrade;
    }

    public ResponseScheduleDto toResponseScheduleDto() {
        return ResponseScheduleDto.builder()
                .id(this.id)
                .clubId(this.clubId)
                .title(this.title)
                .description(this.description)
                .start(this.start)
                .end(this.end)
                .allDay(this.allDay)
//                .disclosedGrade(this.disclosedGrade)
                .build();
    }
}
