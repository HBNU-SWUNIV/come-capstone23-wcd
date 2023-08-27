package com.wcd.boardservice.dto.vote.voteitem;

import com.wcd.boardservice.dto.vote.voterecord.VoteRecordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemDto {
    private Long id;
    private List<VoteRecordDto> voteRecordDtos;
    private String vote_item_value;
}
