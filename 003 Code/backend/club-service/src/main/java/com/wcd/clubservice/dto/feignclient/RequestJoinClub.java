package com.wcd.clubservice.dto.feignclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class RequestJoinClub {

    private Long clubId;
    private Long userId;

    @Builder
    public RequestJoinClub(Long clubId, Long userId) {
        this.clubId = clubId;
        this.userId = userId;
    }
}
