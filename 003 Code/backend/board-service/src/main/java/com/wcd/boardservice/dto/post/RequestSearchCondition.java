package com.wcd.boardservice.dto.post;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class RequestSearchCondition {
    String category;
    String writer;
    String title;
    String content;
}
