package com.wcd.scheduleservice.service;

import com.wcd.scheduleservice.dto.RequestScheduleDto;
import com.wcd.scheduleservice.dto.ResponseScheduleDto;
import com.wcd.scheduleservice.entity.Schedule;
import com.wcd.scheduleservice.repository.ScheduleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseScheduleDto createSchedule(RequestScheduleDto requestScheduleDto) {
        Schedule newSchedule = modelMapper.map(requestScheduleDto, Schedule.class);
        Schedule savedSchedule = scheduleRepository.save(newSchedule);
        ResponseScheduleDto responseScheduleDto = modelMapper.map(savedSchedule, ResponseScheduleDto.class);
        return responseScheduleDto;
    }

    @Override
    public ResponseScheduleDto updateSchedule(Long scheduleId, RequestScheduleDto requestScheduleDto) {
        try {
            Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new Exception());
            schedule = modelMapper.map(requestScheduleDto, Schedule.class);

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
            LocalDate month = LocalDate.parse(yymm);
            LocalDate startDay = month.withDayOfMonth(1);
            LocalDate endDay = month.withDayOfMonth(month.lengthOfMonth());
            LocalDateTime startTime = startDay.atStartOfDay();
            LocalDateTime endTime = endDay.atTime(LocalTime.MAX);
            List<Schedule> schedules = scheduleRepository.findByClubIdEqualsAndStartTimeOrEndTimeBetween(clubId, startTime, endTime);
            List<ResponseScheduleDto> scheduleDtos = (List<ResponseScheduleDto>)schedules.stream().map((schedule) -> modelMapper.map(schedule, ResponseScheduleDto.class));
            return scheduleDtos;
        } catch (Exception e) {
            return null;
        }
    }
}
