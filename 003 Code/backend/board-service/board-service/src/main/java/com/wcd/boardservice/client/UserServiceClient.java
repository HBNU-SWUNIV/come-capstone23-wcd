package com.wcd.boardservice.client;

import com.wcd.boardservice.dto.user.RequestUserNamesDto;
import com.wcd.boardservice.dto.user.ResponseUserNamesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name="user-service")
public interface UserServiceClient {

    @PostMapping("/user/userNames")
    ResponseUserNamesDto getUserNames(RequestUserNamesDto requestUserNamesDto);
}

