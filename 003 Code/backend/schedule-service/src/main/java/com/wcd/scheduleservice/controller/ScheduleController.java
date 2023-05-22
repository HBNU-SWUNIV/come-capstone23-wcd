package com.wcd.scheduleservice.controller;

import com.wcd.scheduleservice.dto.RequestScheduleDto;
import com.wcd.scheduleservice.dto.ResponseScheduleDto;
import com.wcd.scheduleservice.service.ScheduleService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clubs/{club-id}/calendars")
public class ScheduleController {

    Environment env;
    ScheduleService scheduleService;

    public ScheduleController(Environment env, ScheduleService scheduleService) {
        this.env = env;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ResponseScheduleDto>> getClubMonthSchedule(@PathVariable("club-id") Long clubId, @RequestParam String yymm) {
        List<ResponseScheduleDto> responseScheduleDtos = scheduleService.getClubMonthSchedules(clubId, yymm);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDtos);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseScheduleDto> createClubSchedule(@PathVariable("club-id") Long clubId, @RequestBody RequestScheduleDto requestScheduleDto) {
        ResponseScheduleDto responseScheduleDto = scheduleService.createSchedule(requestScheduleDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDto);
    }

    @GetMapping("/{calandars-id}")
    public ResponseEntity<ResponseScheduleDto> getSchedule(@PathVariable("club-id") Long clubId, @PathVariable("calandars-id") Long scheduleId) {
        ResponseScheduleDto responseScheduleDto = scheduleService.getScheduleById(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(responseScheduleDto);
    }
}
