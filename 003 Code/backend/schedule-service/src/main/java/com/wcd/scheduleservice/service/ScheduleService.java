package com.wcd.scheduleservice.service;

import com.wcd.scheduleservice.dto.RequestScheduleDto;
import com.wcd.scheduleservice.dto.ResponseScheduleDto;

import java.util.List;

public interface ScheduleService {
    public ResponseScheduleDto createSchedule(Long clubId, RequestScheduleDto requestScheduleDto);
    public ResponseScheduleDto updateSchedule(Long clubId, Long scheduleId, RequestScheduleDto requestScheduleDto);
    public void deleteSchedule(Long scheduleId);
    public ResponseScheduleDto getScheduleById(Long scheduleId);
    public List<ResponseScheduleDto> getClubMonthSchedules(Long clubId, String yymm);
}
