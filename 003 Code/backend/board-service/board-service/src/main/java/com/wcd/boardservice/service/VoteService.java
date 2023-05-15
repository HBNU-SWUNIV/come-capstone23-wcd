package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.vote.RequestVoteDto;
import com.wcd.boardservice.dto.vote.ResponseVoteDto;
import com.wcd.boardservice.dto.vote.VoteDto;

public interface VoteService {
    ResponseVoteDto createVote(RequestVoteDto requestVoteDto);
    ResponseVoteDto updateVote(Long voteId, Long userId, RequestVoteDto requestVoteDto);
    void deleteVote(Long voteId, Long userId);
    ResponseVoteDto getVoteById(Long voteId);
    ResponseVoteDto castVote(Long voteId, Long[] itemIds, Long userId);
    ResponseVoteDto reCastVote(Long voteId, Long[] itemIds, Long userId);
    ResponseVoteDto unCastVote(Long voteId, Long userId);
    ResponseVoteDto addItem(Long voteId, VoteDto voteDto);
}
