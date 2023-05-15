package com.wcd.boardservice.dto.vote;

import com.wcd.boardservice.dto.vote.voteitem.VoteItemDto;
import com.wcd.boardservice.dto.vote.voterecord.VoteRecordDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVoteDto {
    private Long id;
    private Long clubId;
    private Long writerId;
    private LocalDateTime deadline;
    private Boolean isMultipleSelection;
    private Boolean isBlind;
    private boolean isAddItem;
    private List<VoteItemDto> voteItemDtos;
    private List<VoteRecordDto> voteRecordDtos;
}
