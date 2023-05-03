package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.CommentDto;
import com.wcd.boardservice.service.CommentService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clubs/{club-id}/posts/{post-id}/comments/")
public class UserCommentController {

    Environment env;
    CommentService commentService;

    public UserCommentController(Environment env, CommentService commentService) {
        this.env = env;
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public ResponseEntity<Page<CommentDto>> getAllUserComments(@PathVariable("user-id") Long userId, Pageable pageable) {
        Page<CommentDto> commentDtos = commentService.getALlUserComment(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(commentDtos);
    }
}