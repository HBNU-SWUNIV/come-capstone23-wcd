package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.VoteDto;
import com.wcd.boardservice.entity.VoteItem;

public interface VoteService {
    VoteDto createVote(VoteDto voteDto);
    VoteDto updateVote(Long voteId, Long userId, VoteDto voteDto);
    void deleteVote(Long voteId, Long userId);
    VoteDto getVoteById(Long voteId);
    VoteDto castVote(Long voteId, Long[] itemIds, Long userId);
    VoteDto reCastVote(Long voteId, Long[] itemIds, Long userId);
    VoteDto unCastVote(Long voteId, Long userId);
    VoteDto addItem(Long voteId, VoteDto voteDto);
}
