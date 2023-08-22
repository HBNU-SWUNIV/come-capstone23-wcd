package com.wcd.scheduleservice.dto;

import com.wcd.scheduleservice.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestScheduleDto {
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private boolean allDay;
    private String disclosedGrade;

    public Schedule toEntity(Long clubId) {
        return Schedule.builder()
                .clubId(clubId)
                .title(this.title)
                .description(this.description)
                .start(this.start)
                .end(this.end)
                .allDay(this.allDay)
                .disclosedGrade(this.disclosedGrade)
                .build();
    }
}
