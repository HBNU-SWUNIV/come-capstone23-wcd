package com.wcd.boardservice.dto.vote.voterecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVoteRecordDto {
    private Long id;
    private Long voteItemId;
    private Long userId;
}
