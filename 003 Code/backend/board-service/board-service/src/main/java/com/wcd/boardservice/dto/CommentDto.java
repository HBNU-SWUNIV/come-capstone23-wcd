package com.wcd.boardservice.dto;

import com.wcd.boardservice.entity.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private Post post;
    private Long writerId;
    private String content;
    private int step;
    private int group;
    private int order;
    private LocalDateTime createdAt;
}
