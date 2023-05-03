package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.CommentDto;
import com.wcd.boardservice.service.CommentService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs/{club-id}/posts/{post-id}/comments/")
public class ClubCommentController {

    Environment env;
    CommentService commentService;

    public ClubCommentController(Environment env, CommentService commentService) {
        this.env = env;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<CommentDto>> getAllPostComments(@PathVariable("club-id") Long clubId,
                                                              @PathVariable("post-id") Long postId,
                                                              Pageable pageable) {
        Page<CommentDto> commentListDtos = commentService.getAllPostComment(postId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(commentListDtos);
    }

    @PostMapping("/")
    public ResponseEntity<CommentDto> createNewComment(@PathVariable("club-id") Long clubId,
                                                       @PathVariable("post-id") Long postId,
                                                       @RequestBody CommentDto commentDto) {
        CommentDto createCommentDto = commentService.createComment(commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(createCommentDto);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("club-id") Long clubId,
                                                    @PathVariable("post-id") Long postId,
                                                    @PathVariable("comment-id") Long commentId,
                                                    @RequestBody CommentDto commentDto) {
        CommentDto updateCommentDto = commentService.updateComment(commentId, commentDto.getWriterId(), commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateCommentDto);
    }

    @DeleteMapping("/{comment-id}")
    public HttpStatus deleteComment(@PathVariable("club-id") Long clubId,
                                    @PathVariable("post-id") Long postId,
                                    @PathVariable("comment-id") Long commentId,
                                    @RequestBody Long userId) {
        commentService.deleteComment(commentId, userId);
        return HttpStatus.OK;
    }
}
