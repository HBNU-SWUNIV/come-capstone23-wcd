package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.VoteDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;
    private String category;
    private Long clubId;
    private Long writerId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private VoteDto voteDto;
}
