package com.wcd.scheduleservice.dto;

import com.wcd.scheduleservice.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseScheduleDto {
    private Long id;
    private Long clubId;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean allDay;
//    private String disclosedGrade;

    @Builder
    public ResponseScheduleDto(Long id, Long clubId, String title, String description, LocalDateTime start, LocalDateTime end, boolean allDay, String disclosedGrade) {
        this.id = id;
        this.clubId = clubId;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
//        this.disclosedGrade = disclosedGrade;
    }
}
