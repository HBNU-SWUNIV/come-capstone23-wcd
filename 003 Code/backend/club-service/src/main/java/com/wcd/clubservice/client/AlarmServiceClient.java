package com.wcd.clubservice.client;

import com.wcd.clubservice.dto.feignclient.RequestJoinClub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "alarm-service")
public interface AlarmServiceClient {

    @PostMapping("/join-club")
    void notifyJoinClub(@RequestBody RequestJoinClub requestJoinClub);
}
