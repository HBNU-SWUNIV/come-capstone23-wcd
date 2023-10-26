package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.post.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Long createPost(Long clubId, Long userId, RequestPostDto responsePostDto);
    ResponsePostDto updatePost(Long postId, Long userId, UpdateRequestPostDto updateRequestPostDto);
    void deletePost(Long userId, Long postId);
    ResponsePostDto getPostById(Long postId);
    Page<ResponsePostListDto> getUserPostList(Long userId, Pageable pageable);
    Page<ResponsePostListDto> getClubPostList(Long clubId, RequestSearchCondition requestSearchCondition, Pageable pageable);
//    VoteDto castVote(Long voteId, Long[] itemIds, Long userId);
//    VoteDto reCastVote(Long voteId, Long[] itemIds, Long userId);
//    VoteDto unCastVote(Long voteId, Long userId);
//    VoteDto addItem(Long voteId, VoteDto voteDto);
}
