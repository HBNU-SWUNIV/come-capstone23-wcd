package com.wcd.alarmservice.controller;

import com.wcd.alarmservice.dto.request.RequestJoinClub;
import com.wcd.alarmservice.dto.request.RequestTest;
import com.wcd.alarmservice.service.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class SseController {
    private final SseService notificationService;

    @GetMapping(value="/connect/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.connect(userId));
    }

    @PostMapping("/join-club")
    public void notifyJoinClub(@RequestBody RequestJoinClub requestJoinClub) {
        notificationService.notifyJoinClub(requestJoinClub);
    }

    @PostMapping("/create-schedule/{clubId}")
    public void notifyCreateSchedule(@PathVariable Long clubId) {
        notificationService.notifyCreateSchedule(clubId);
    }


    @PostMapping("/sse/test")
    public String test(@RequestBody RequestTest requestTest) {
        notificationService.test(requestTest.getNum());

        return "hi";
    }

    @GetMapping("test")
    public String testapi() {
        return "test";
    }
}
