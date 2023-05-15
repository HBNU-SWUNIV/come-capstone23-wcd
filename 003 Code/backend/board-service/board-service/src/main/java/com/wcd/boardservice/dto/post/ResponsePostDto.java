package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.vote.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePostDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private String category;
    private Long clubId;
    private Long writerId;
    private String title;
    private String content;
    private VoteDto voteDto;
}
