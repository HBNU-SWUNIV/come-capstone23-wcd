package com.wcd.boardservice.controller;

import com.wcd.boardservice.client.UserServiceClient;
import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.dto.post.RequestPostDto;
import com.wcd.boardservice.dto.post.ResponsePostDto;
import com.wcd.boardservice.service.PostService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clubs/{club-id}/posts")
public class ClubPostController {
    Environment env;
    PostService postService;

    public ClubPostController(Environment env, PostService postService) {
        this.env = env;
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<ResponsePostListDto>> getAllCLubPosts(@PathVariable("club-id") Long clubId, Pageable pageable) {
        Page<ResponsePostListDto> responsePostListDtos = postService.getAllClubPost(clubId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responsePostListDtos);
    }

    @PostMapping("/")
    public ResponseEntity<ResponsePostDto> createNewPost(@PathVariable("club-id") Long clubId, @RequestHeader("user-id") Long userId, @RequestBody RequestPostDto requestPostDto) {
        ResponsePostDto responsePostDto = postService.createPost(userId, requestPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }

    @GetMapping("/{post-id}")
    public ResponseEntity<ResponsePostDto> getPost(@PathVariable("club-id") Long clubId, @PathVariable("post-id") Long postId) {
        ResponsePostDto responsePostDto = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }

    @PatchMapping("/{post-id}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable("club-id") Long clubId, @PathVariable("post-id") Long postId, @RequestBody RequestPostDto requestPostDto) {
        ResponsePostDto responsePostDto = postService.updatePost(postId, requestPostDto.getWriterId(), requestPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }

    @DeleteMapping("/{post-id}")
    public HttpStatus deletePost(@PathVariable("club-id") Long clubId, @PathVariable("post-id") Long postId, @RequestBody Long userId) {
        postService.deletePost(userId, postId);
        return HttpStatus.OK;
    }
}
