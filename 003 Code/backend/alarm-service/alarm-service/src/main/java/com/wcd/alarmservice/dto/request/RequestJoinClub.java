package com.wcd.alarmservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestJoinClub {

    private Long clubId;
    private Long userId;
}
