package com.wcd.alarmservice.client;

import com.wcd.alarmservice.dto.feignclient.ResponseClubMemberIdsByClubId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "club-service")
public interface ClubServiceClient {

    @GetMapping("/clubs/{club-id}/members/id")
    ResponseClubMemberIdsByClubId getClubMemberIds(@PathVariable("club-id") Long clubId);

    @GetMapping("/clubs/{club-id}/clubName")
    String getClubNameById(@PathVariable("club-id") Long clubId);
}
