package com.wcd.boardservice.controller;

import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.dto.post.RequestPostDto;
import com.wcd.boardservice.dto.post.ResponsePostDto;
import com.wcd.boardservice.dto.post.UpdateRequestPostDto;
import com.wcd.boardservice.service.PostService;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clubs/{club-id}")
public class ClubPostController {
    Environment env;
    PostService postService;

    public ClubPostController(Environment env, PostService postService) {
        this.env = env;
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> createNewPost(@PathVariable("club-id") Long clubId,
                                                         @RequestHeader("user-id") Long writerId,
                                                         @RequestBody RequestPostDto requestPostDto) {
        Long id = postService.createPost(clubId, writerId, requestPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PatchMapping("/posts/{post-id}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable("club-id") Long clubId,
                                                      @PathVariable("post-id") Long postId,
                                                      @RequestHeader("user-id") Long writerId,
                                                      @RequestBody UpdateRequestPostDto updateRequestPostDto) {
        ResponsePostDto responsePostDto = postService.updatePost(postId, writerId, updateRequestPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }

    @DeleteMapping("/posts/{post-id}")
    public HttpStatus deletePost(@PathVariable("club-id") Long clubId,
                                 @PathVariable("post-id") Long postId,
                                 @RequestHeader("user-id") Long writerId) {
        postService.deletePost(writerId, postId);
        return HttpStatus.OK;
    }

    @GetMapping("/posts/{post-id}")
    public ResponseEntity<ResponsePostDto> getPost(@PathVariable("club-id") Long clubId,
                                                   @PathVariable("post-id") Long postId) {
        ResponsePostDto responsePostDto = postService.getPostById(postId);
        return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<ResponsePostListDto>> getAllPostsInClub(@PathVariable("club-id") Long clubId,
                                                                       Pageable pageable) {
        Page<ResponsePostListDto> responsePostListDtos = postService.getAllPostInClub(clubId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(responsePostListDtos);
    }
}
