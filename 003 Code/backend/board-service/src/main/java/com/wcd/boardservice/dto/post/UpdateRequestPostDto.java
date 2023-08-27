package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.vote.RequestVoteDto;
import lombok.Data;

@Data
public class UpdateRequestPostDto {
    private String category;
    private String title;
    private String content;
    private RequestVoteDto requestVoteDto;
}
