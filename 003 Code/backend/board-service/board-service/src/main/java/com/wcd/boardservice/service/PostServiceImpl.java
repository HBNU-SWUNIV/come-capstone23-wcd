package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.VoteDto;
import com.wcd.boardservice.dto.post.PostDto;
import com.wcd.boardservice.dto.post.PostListDto;
import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    PostRepository postRepository;
    VoteService voteService;
    ModelMapper modelMapper;

    @Override
    public PostDto creatPost(PostDto postDto) {
        try {
            Post newPost = modelMapper.map(postDto, Post.class);
            if (newPost.getVote() != null) {
                VoteDto newVoteDto = modelMapper.map(newPost.getVote(), VoteDto.class);
                voteService.createVote(newVoteDto);
            }
            Post savePost = postRepository.save(newPost);
            PostDto savePostDto = modelMapper.map(savePost, PostDto.class);

            return savePostDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PostDto updatePost(Long postId, Long userId, PostDto postDto) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());

            if (!post.getWriterId().equals(userId)) {
                throw new Exception();
            }

            if (postDto.getVoteDto().equals(null)) {
                voteService.deleteVote(post.getVote().getId(), userId);
            }

            post = modelMapper.map(postDto, Post.class);

            Post updatePost = postRepository.save(post);
            PostDto updatePostDto = modelMapper.map(updatePost, PostDto.class);

            return updatePostDto;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deletePost(Long userId, Long postId) {
        try {
            Post deletePost = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
            if (!deletePost.getWriterId().equals(userId)) {
                throw new Exception("");
            }
            if (!deletePost.getVote().equals(null)) {
                voteService.deleteVote(deletePost.getVote().getId(), userId);
            }
            postRepository.delete(deletePost);
        } catch (NoSuchElementException e) {

        } catch (Exception e) {

        }
    }

    @Override
    public PostDto getPostById(Long postId) {
        try {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException());
            PostDto postDto = modelMapper.map(post, PostDto.class);

            return postDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<PostListDto> getAllPost(Pageable pageable) {
        try {
            Page<Post> postList= postRepository.findAll(pageable);
            List<PostListDto> postDtoList = postList.stream()
                    .map(post -> modelMapper.map(post, PostListDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(postDtoList, pageable, postList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<PostListDto> getAllClubPost(Long clubId, Pageable pageable) {
        try {
            Page<Post> postList= postRepository.findByclubId(clubId, pageable);
            List<PostListDto> postDtoList = postList.stream()
                    .map(post -> modelMapper.map(post, PostListDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(postDtoList, pageable, postList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<PostListDto> getAllUserPost(Long userId, Pageable pageable) {
        try {
            Page<Post> postList= postRepository.findByuserId(userId, pageable);
            List<PostListDto> postDtoList = postList.stream()
                    .map(post -> modelMapper.map(post, PostListDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(postDtoList, pageable, postList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }
}
