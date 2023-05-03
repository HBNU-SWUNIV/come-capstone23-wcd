package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.post.PostDto;
import com.wcd.boardservice.dto.post.PostListDto;
import com.wcd.boardservice.service.PostService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
public class ClubPostController {
    Environment env;
    PostService postService;

    public ClubPostController(Environment env, PostService postService) {
        this.env = env;
        this.postService = postService;
    }

    @GetMapping("/clubs/{club-id}/posts")
    public ResponseEntity<Page<PostListDto>> getAllCLubPosts(@PathVariable("club-id") Long clubId, Pageable pageable) {
        Page<PostListDto> postListDtos = postService.getAllClubPost(clubId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(postListDtos);
    }

    @PostMapping("/clubs/{club-id}/posts")
    public ResponseEntity<PostDto> createNewPost(@PathVariable("club-id") Long clubId, @RequestBody PostDto postDto) {
        PostDto createPostDto = postService.creatPost(postDto);
        return ResponseEntity.status(HttpStatus.OK).body(createPostDto);
    }

    @GetMapping("/clubs/{club-id}/posts/{post-id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("club-id") Long clubId, @PathVariable("post-id") Long postId) {
        PostDto getPostDto = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(getPostDto);
    }

    @PatchMapping("/clubs/{club-id}/posts/{post-id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("club-id") Long clubId, @PathVariable("post-id") Long postId, @RequestBody PostDto postDto) {
        PostDto updatePostDto = postService.updatePost(postId, postDto.getWriterId(), postDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatePostDto);
    }

    @DeleteMapping("/clubs/{club-id}/posts/{post-id}")
    public HttpStatus deletePost(@PathVariable("club-id") Long clubId, @PathVariable("post-id") Long postId, @RequestBody Long userId) {
        postService.deletePost(userId, postId);
        return HttpStatus.OK;
    }
}
