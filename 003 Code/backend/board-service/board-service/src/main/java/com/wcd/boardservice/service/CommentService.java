package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    CommentDto createComment(CommentDto commentDto);
    CommentDto updateComment(Long commentId, Long userId, CommentDto commentDto);
    void deleteComment(Long commentId, Long userId);
    CommentDto getCommentById(Long commentId);
    Page<CommentDto> getAllComment(Pageable pageable);
    Page<CommentDto> getAllPostComment(Long postId, Pageable pageable);
    Page<CommentDto> getALlUserComment(Long userId, Pageable pageable);
    Page<CommentDto> getAllClubComment(Long clubId, Pageable pageable);
}
