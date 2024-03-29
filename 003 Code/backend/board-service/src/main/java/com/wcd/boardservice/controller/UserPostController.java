package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.service.PostService;
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
@RequestMapping("/users/{user-id}")
public class UserPostController {
    Environment env;
    PostService postService;

    public UserPostController(Environment env, PostService postService) {
        this.env = env;
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<ResponsePostListDto>> getUserAllPosts(@PathVariable("user-id") Long userId, Pageable pageable) {
        Page<ResponsePostListDto> postListDtos = postService.getUserPostList(userId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(postListDtos);
    }
}

