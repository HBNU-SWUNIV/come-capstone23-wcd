package com.wcd.chattingservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Schema(description = "사용자 이름 응답")
@Getter
@NoArgsConstructor
public class ResponseUsernames {

    private Map<String, String> userNames;

}
