package com.wcd.chattingservice.client;

import com.wcd.chattingservice.dto.RequestUsernames;
import com.wcd.chattingservice.dto.response.ResponseUsernames;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @GetMapping("/user/{user-id}/userName")
    String getUsernameById(@PathVariable("user-id") Long userId);

    @PostMapping("/user/userNames")
    ResponseUsernames getUsernamesByIds(@RequestBody RequestUsernames requestUsernames);
}
