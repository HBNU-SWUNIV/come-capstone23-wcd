package com.wcd.clubservice.client;

import com.wcd.clubservice.dto.RequestUserNamesDto;
import com.wcd.clubservice.dto.ResponseUserNamesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @PostMapping("/user/userNames")
    ResponseUserNamesDto getUserNames(RequestUserNamesDto requestUserNamesDto);

    @GetMapping("/user/{user-id}/userName")
    String getUserNameById(@PathVariable("user-id") Long userId);
}
