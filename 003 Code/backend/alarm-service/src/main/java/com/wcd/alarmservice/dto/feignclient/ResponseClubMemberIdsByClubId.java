package com.wcd.alarmservice.dto.feignclient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseClubMemberIdsByClubId {

    private List<Long> userIdList = new ArrayList<>();
}
