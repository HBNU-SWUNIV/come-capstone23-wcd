package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.vote.RequestVoteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostDto {
    private String category;
    private String title;
    private String content;
    private RequestVoteDto requestVoteDto;
}
