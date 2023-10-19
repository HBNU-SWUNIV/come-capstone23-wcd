package com.wcd.fcmservice.fcmservice.controller;


import com.wcd.fcmservice.fcmservice.dto.request.RequestChatMessage;
import com.wcd.fcmservice.fcmservice.dto.request.RequestSubscribe;
import com.wcd.fcmservice.fcmservice.dto.request.RequestUnsubscribe;
import com.wcd.fcmservice.fcmservice.service.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class FCMController {

    private final FCMService fcmService;

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Void> subscribe(@RequestBody RequestSubscribe requestSubscribe) {
        fcmService.subscribeTopic(requestSubscribe);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe(@RequestBody RequestUnsubscribe requestUnsubscribe) {
        fcmService.unsubscribeTopic(requestUnsubscribe);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/sendChatMessage")
    public ResponseEntity<Void> sendChatMessage(@RequestBody RequestChatMessage requestChatMessage) {
        fcmService.sendChatMessage(requestChatMessage);

        return ResponseEntity.noContent().build();
    }
}
