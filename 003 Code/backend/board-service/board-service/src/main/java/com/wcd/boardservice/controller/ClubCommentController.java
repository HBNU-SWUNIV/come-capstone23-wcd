package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.comment.RequestCommentDto;
import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.dto.comment.UpdateRequestCommentDto;
import com.wcd.boardservice.service.CommentService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clubs/{club-id}/posts/{post-id}/")
public class ClubCommentController {

    Environment env;
    CommentService commentService;

    public ClubCommentController(Environment env, CommentService commentService) {
        this.env = env;
        this.commentService = commentService;
    }
    @PostMapping("/comments")
    public ResponseEntity<ResponseCommentDto> createNewComment(@PathVariable("club-id") Long clubId,
                                                               @PathVariable("post-id") Long postId,
                                                               @RequestHeader("user-id") Long writerId,
                                                               @RequestBody RequestCommentDto requestCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.createComment(clubId, postId, writerId, requestCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseCommentDto);
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity<ResponseCommentDto> updateComment(@PathVariable("club-id") Long clubId,
                                                            @PathVariable("post-id") Long postId,
                                                            @RequestHeader("user-id") Long writerId,
                                                            @PathVariable("comment-id") Long commentId,
                                                            @RequestBody UpdateRequestCommentDto updateRequestCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.updateComment(commentId, writerId, updateRequestCommentDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseCommentDto);
    }

    @DeleteMapping("/comments/{comment-id}")
    public HttpStatus deleteComment(@PathVariable("club-id") Long clubId,
                                    @PathVariable("post-id") Long postId,
                                    @PathVariable("comment-id") Long commentId,
                                    @RequestHeader("user-id") Long writerId) {
        commentService.deleteComment(commentId, writerId);
        return HttpStatus.OK;
    }

    @GetMapping("/comments")
    public ResponseEntity<Page<ResponseCommentDto>> getAllCommentsInPost(@PathVariable("club-id") Long clubId,
                                                                         @PathVariable("post-id") Long postId,
                                                                         Pageable pageable) {
        Page<ResponseCommentDto> commentListDtos = commentService.getAllCommentsInPost(postId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(commentListDtos);
    }
}
