package com.wcd.boardservice.dto.vote;

import com.wcd.boardservice.dto.vote.voteitem.RequestVoteItemDto;
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
public class RequestVoteDto {
    private Long clubId;
    private Long writerId;
    private LocalDateTime deadline;
    private Boolean isMultipleSelection;
    private Boolean isBlind;
    private boolean isAddItem;
    private List<RequestVoteItemDto> requestVoteItemDtos;
}
