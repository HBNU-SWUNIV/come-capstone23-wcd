package com.wcd.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="club-service")
public interface ClubServiceClient {

    @DeleteMapping("/club-service/users/{user-id}")
    void deleteMember(@PathVariable("user-id") Long userId);
}
