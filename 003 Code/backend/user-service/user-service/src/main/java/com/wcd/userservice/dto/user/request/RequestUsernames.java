package com.wcd.userservice.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "사용자 이름 요청ㅇ")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUsernames {

    private List<Long> userIds;
}
