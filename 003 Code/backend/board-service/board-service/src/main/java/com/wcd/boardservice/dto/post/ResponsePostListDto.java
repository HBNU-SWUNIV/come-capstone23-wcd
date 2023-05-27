package com.wcd.boardservice.dto.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponsePostListDto {
    private Long id;
    private String category;
    private Long clubId;
    private Long writerId;
    private String title;
    private LocalDateTime createdAt;
    private Long voteId;
}
