package com.wcd.chattingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @GetMapping("/user-service/user/{user-id}/userName")
    String getUsernameById(@PathVariable("user-id") Long userId);
}
