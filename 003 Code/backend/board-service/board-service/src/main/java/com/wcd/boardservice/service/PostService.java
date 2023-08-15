package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.dto.post.RequestPostDto;
import com.wcd.boardservice.dto.post.ResponsePostDto;
import com.wcd.boardservice.dto.post.UpdateRequestPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Long createPost(Long clubId, Long userId, RequestPostDto responsePostDto);
    ResponsePostDto updatePost(Long postId, Long userId, UpdateRequestPostDto updateRequestPostDto);
    void deletePost(Long userId, Long postId);
    ResponsePostDto getPostById(Long postId);
    Page<ResponsePostListDto> getAllPost(Pageable pageable);
    Page<ResponsePostListDto> getAllPostInClub(Long clubId, Pageable pageable);
    Page<ResponsePostListDto> getAllPostByUser(Long userId, Pageable pageable);
    Page<ResponsePostListDto> getAllPostByUserInClub(Long clubId, Long userId, Pageable pageable);
//    VoteDto castVote(Long voteId, Long[] itemIds, Long userId);
//    VoteDto reCastVote(Long voteId, Long[] itemIds, Long userId);
//    VoteDto unCastVote(Long voteId, Long userId);
//    VoteDto addItem(Long voteId, VoteDto voteDto);
}
