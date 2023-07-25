package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.dto.post.RequestPostDto;
import com.wcd.boardservice.dto.post.ResponsePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    ResponsePostDto createPost(Long userId, RequestPostDto responsePostDto);
    ResponsePostDto updatePost(Long postId, Long userId, RequestPostDto responsePostDto);
    void deletePost(Long userId, Long postId);
    ResponsePostDto getPostById(Long postId);
    Page<ResponsePostListDto> getAllPost(Pageable pageable);
    Page<ResponsePostListDto> getAllClubPost(Long clubId, Pageable pageable);
    Page<ResponsePostListDto> getAllUserPost(Long userId, Pageable pageable);
//    VoteDto castVote(Long voteId, Long[] itemIds, Long userId);
//    VoteDto reCastVote(Long voteId, Long[] itemIds, Long userId);
//    VoteDto unCastVote(Long voteId, Long userId);
//    VoteDto addItem(Long voteId, VoteDto voteDto);
}
