package com.wcd.scheduleservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "alarm-service")
public interface AlarmServiceClient {

    @PostMapping("/create-schedule/{clubId}")
    void notifyCreateSchedule(@PathVariable Long clubId);
}
