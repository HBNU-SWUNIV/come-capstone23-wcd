package com.wcd.userservice.client;

import com.wcd.userservice.dto.club.ResponseClub;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="club-service")
public interface ClubServiceClient {

    @GetMapping("/club-service/clubs/member/{user-id}")
    List<ResponseClub> getClubByUserId(@PathVariable Long userId);

    @DeleteMapping("/club-service/member/{user-id}")
    void deleteMember(@PathVariable("user-id") Long userId);
}
