package com.wcd.scheduleservice.controller;

import com.wcd.scheduleservice.dto.RequestScheduleDto;
import com.wcd.scheduleservice.dto.ResponseScheduleDto;
import com.wcd.scheduleservice.service.ScheduleService;

import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clubs/{club-id}")
public class ScheduleController {

    private final Environment env;
    private final ScheduleService scheduleService;

    @GetMapping("/calendars")
    public ResponseEntity<List<ResponseScheduleDto>> getClubMonthSchedule(@PathVariable("club-id") Long clubId, @RequestParam String yymm) {
        List<ResponseScheduleDto> responseScheduleDtos = scheduleService.getClubMonthSchedules(clubId, yymm);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDtos);
    }

    @PostMapping("/calendars")
    public ResponseEntity<ResponseScheduleDto> createClubSchedule(@PathVariable("club-id") Long clubId, @RequestBody RequestScheduleDto requestScheduleDto) {
        ResponseScheduleDto responseScheduleDto = scheduleService.createSchedule(clubId, requestScheduleDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDto);
    }

    @GetMapping("/calendars/{calendars-id}")
    public ResponseEntity<ResponseScheduleDto> getSchedule(@PathVariable("club-id") Long clubId, @PathVariable("calendars-id") Long scheduleId) {
        ResponseScheduleDto responseScheduleDto = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDto);
    }

    @PatchMapping("/calendars/{calendars-id}")
    public ResponseEntity<ResponseScheduleDto> updateSchedule(@PathVariable("club-id") Long clubId, @PathVariable("calendars-id") Long scheduleId, @RequestBody RequestScheduleDto requestScheduleDto) {
        ResponseScheduleDto responseScheduleDto = scheduleService.updateSchedule(clubId, scheduleId, requestScheduleDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDto);
    }

    @DeleteMapping("/calendars/{calendars-id}")
    public HttpStatus deleteSchedule(@PathVariable("club-id") Long clubId, @PathVariable("calendars-id") Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return HttpStatus.NO_CONTENT;
    }
}
