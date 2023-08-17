package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.service.CommentService;
import com.wcd.boardservice.service.PostService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clubs/{club-id}/users/{user-id}")
public class ClubUserController {
    Environment env;
    PostService postService;
    CommentService commentService;

    public ClubUserController(Environment env, PostService postService, CommentService commentService) {
        this.env = env;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<ResponsePostListDto>> getAllPostsByUserInClub(@PathVariable("club-id") Long clubId,
                                                                             @PathVariable("user-id") Long userId,
                                                                             Pageable pageable) {
        Page<ResponsePostListDto> responsePostListDtos = postService.getAllPostByUserInClub(clubId, userId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responsePostListDtos);
    }

    @GetMapping("comments")
    public ResponseEntity<Page<ResponseCommentDto>> getAllCommentsByUserInClub(@PathVariable("club-id") Long clubId,
                                                                               @RequestHeader("user-id") Long writerId,
                                                                               Pageable pageable) {
        Page<ResponseCommentDto> commentListDtos = commentService.getAllCommentsByUserInClub(clubId, writerId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(commentListDtos);
    }
}
