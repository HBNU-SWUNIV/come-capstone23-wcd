package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.comment.RequestCommentDto;
import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.dto.comment.UpdateRequestCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    ResponseCommentDto createComment(Long clubId, Long postId, Long writerId, RequestCommentDto requestCommentDto);
    ResponseCommentDto updateComment(Long commentId, Long userId, UpdateRequestCommentDto updateRequestCommentDto);
    void deleteComment(Long commentId, Long userId);
    ResponseCommentDto getCommentById(Long commentId);
    Page<ResponseCommentDto> getAllComment(Pageable pageable);
    Page<ResponseCommentDto> getAllCommentsInPost(Long postId, Pageable pageable);
    Page<ResponseCommentDto> getAllCommentsByUserInClub(Long clubId, Long userId, Pageable pageable);
    Page<ResponseCommentDto> getALlUserComment(Long userId, Pageable pageable);
//    Page<CommentDto> getAllClubComment(Long clubId, Pageable pageable);
}
