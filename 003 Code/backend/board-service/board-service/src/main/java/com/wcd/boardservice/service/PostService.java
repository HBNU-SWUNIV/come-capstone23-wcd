package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.post.PostDto;
import com.wcd.boardservice.dto.post.PostListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDto creatPost(PostDto postDto);
    PostDto updatePost(Long postId, Long userId, PostDto postDto);
    void deletePost(Long userId, Long postId);
    PostDto getPostById(Long postId);
    Page<PostListDto> getAllPost(Pageable pageable);
    Page<PostListDto> getAllClubPost(Long clubId, Pageable pageable);
    Page<PostListDto> getAllUserPost(Long userId, Pageable pageable);

}
