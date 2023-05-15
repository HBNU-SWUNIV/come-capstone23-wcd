package com.wcd.boardservice.dto.comment;

import com.wcd.boardservice.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommentDto {
    private Post post;
    private Long writerId;
    private String content;
    private int step;
    private int group;
    private int order;
}
