package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.comment.CommentDto;
import com.wcd.boardservice.dto.comment.RequestCommentDto;
import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<Page<ResponseCommentDto>> getAllPostComments(@PathVariable("club-id") Long clubId,
                                                              @PathVariable("post-id") Long postId,
                                                              Pageable pageable) {
        Page<ResponseCommentDto> commentListDtos = commentService.getAllPostComment(postId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(commentListDtos);
    }

    @PostMapping("/")
    public ResponseEntity<ResponseCommentDto> createNewComment(@PathVariable("club-id") Long clubId,
                                                       @PathVariable("post-id") Long postId,
                                                       @RequestBody RequestCommentDto requestCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseCommentDto);
    }

    @PatchMapping("/{comment-id}")
    public ResponseEntity<ResponseCommentDto> updateComment(@PathVariable("club-id") Long clubId,
                                                    @PathVariable("post-id") Long postId,
                                                    @PathVariable("comment-id") Long commentId,
                                                    @RequestBody RequestCommentDto requestCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.updateComment(commentId, requestCommentDto.getWriterId(), requestCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseCommentDto);
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
