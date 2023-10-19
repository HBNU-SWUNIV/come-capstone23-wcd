package com.wcd.fcmservice.fcmservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RequestSubscribe {
    String token;
    List<String> topicList;
}
