package com.wcd.clubservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient
public interface ChattingServiceClient {

    @DeleteMapping("/room/{club_id}")
    void deleteChatRoomByClubId(@PathVariable("club_id") Long clubId);
}
