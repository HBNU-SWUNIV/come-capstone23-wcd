package com.wcd.userservice.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.userservice.entity.Users;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Schema(description = "사용자 이름 응답")
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUsernames {

    private Map<String, String> userNames;

    @Builder
    public ResponseUsernames(List<Users> userList) {
        this.userNames = new HashMap<>();
        for (Users user : userList) {
            userNames.put(user.getId().toString(), user.getName());
        }
    }
}
