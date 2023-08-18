package com.wcd.boardservice.client;

import com.wcd.boardservice.dto.user.RequestUserNamesDto;
import com.wcd.boardservice.dto.user.ResponseUserNamesDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @PostMapping("/user/userNames")
    ResponseUserNamesDto getUserNames(RequestUserNamesDto requestUserNamesDto);

    @GetMapping("/user/{user-id}/userName")
    String getUserNameById(@PathVariable("user-id") Long userId);
}

