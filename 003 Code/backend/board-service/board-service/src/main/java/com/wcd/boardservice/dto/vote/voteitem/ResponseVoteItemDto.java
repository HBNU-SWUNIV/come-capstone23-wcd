package com.wcd.boardservice.dto.vote.voteitem;

import com.wcd.boardservice.dto.vote.voterecord.ResponseVoteRecordDto;
import com.wcd.boardservice.dto.vote.voterecord.VoteRecordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVoteItemDto {
    private Long id;
    private List<ResponseVoteRecordDto> responseVoteRecordDtos;
    private String value;
}
