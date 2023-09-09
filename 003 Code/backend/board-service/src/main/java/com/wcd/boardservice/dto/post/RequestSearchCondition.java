package com.wcd.boardservice.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class RequestSearchCondition {
    Long clubId;
    String category;
    Long writerId;
    String title;
    String content;
}
