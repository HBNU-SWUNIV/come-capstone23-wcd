package com.wcd.boardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @GetMapping("/user/userNames")
    Map<String, String> getUserNames(List<Long> userIds);
}

