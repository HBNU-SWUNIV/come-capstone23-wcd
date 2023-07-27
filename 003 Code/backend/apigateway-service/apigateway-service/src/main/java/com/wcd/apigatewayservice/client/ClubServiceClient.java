package com.wcd.apigatewayservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="club-service")
public interface ClubServiceClient {

    @GetMapping("/member/{user-id}/club/{club-id}")
    boolean isUserMemberOfClub(@PathVariable("club-id") Long clubId, @PathVariable("user-id") Long userId);
}
