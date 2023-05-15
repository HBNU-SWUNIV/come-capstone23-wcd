package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.vote.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostDto {
    private Long id;
    private String category;
    private Long clubId;
    private Long writerId;
    private String title;
    private String content;
    private VoteDto voteDto;
}
