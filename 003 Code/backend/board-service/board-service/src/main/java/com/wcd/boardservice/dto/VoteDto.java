package com.wcd.boardservice.dto;

import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.entity.VoteItem;
import com.wcd.boardservice.entity.VoteRecord;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VoteDto {
    private Long id;
    private Post post;
    private Long clubId;
    private Long writerId;
    private LocalDateTime deadline;
    private Boolean isMultipleSelection;
    private Boolean is_blind;
    private boolean isAddItem;
    private List<VoteItem> voteItems;
    private List<VoteRecord> voteRecords;
}
