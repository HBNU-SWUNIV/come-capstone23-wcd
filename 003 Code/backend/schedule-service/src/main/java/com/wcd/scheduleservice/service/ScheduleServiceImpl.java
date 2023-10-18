package com.wcd.scheduleservice.service;

import com.wcd.scheduleservice.dto.RequestScheduleDto;
import com.wcd.scheduleservice.dto.ResponseScheduleDto;
import com.wcd.scheduleservice.entity.Schedule;
import com.wcd.scheduleservice.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseScheduleDto createSchedule(Long clubId, RequestScheduleDto requestScheduleDto) {
        Schedule newSchedule = requestScheduleDto.toEntity(clubId);
        Schedule savedSchedule = scheduleRepository.save(newSchedule);
        ResponseScheduleDto responseScheduleDto = savedSchedule.toResponseScheduleDto();
        return responseScheduleDto;
    }

    @Override
    public ResponseScheduleDto updateSchedule(Long clubId, Long scheduleId, RequestScheduleDto requestScheduleDto) {
        try {
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new Exception());
            schedule = requestScheduleDto.toEntity(clubId);

            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        try {
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new Exception());
            scheduleRepository.delete(schedule);
        } catch (Exception e) {

        }
    }

    @Override
    public ResponseScheduleDto getScheduleById(Long scheduleId) {
        try {
            Schedule findSchedule = scheduleRepository.findById(scheduleId).orElseThrow();
            ResponseScheduleDto responseScheduleDto = modelMapper.map(findSchedule, ResponseScheduleDto.class);
            return responseScheduleDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ResponseScheduleDto> getClubMonthSchedules(Long clubId, String yymm) {
        try {
            LocalDate month = LocalDate.parse(yymm, DateTimeFormatter.ofPattern("yyMM"));
            LocalDate startDay = month.withDayOfMonth(1);
            LocalDate endDay = month.withDayOfMonth(month.lengthOfMonth());
            LocalDateTime startTime = startDay.atStartOfDay();
            LocalDateTime endTime = endDay.atTime(LocalTime.MAX);
            List<Schedule> schedules = scheduleRepository.findByClubIdEqualsAndStartOrEndBetween(clubId, startTime, endTime);
            List<ResponseScheduleDto> scheduleDtos = schedules.stream().map((schedule) -> schedule.toResponseScheduleDto()).toList();
            return scheduleDtos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
