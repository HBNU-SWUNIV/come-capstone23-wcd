package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.comment.CommentDto;
import com.wcd.boardservice.dto.comment.RequestCommentDto;
import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.entity.Comment;
import com.wcd.boardservice.entity.Post;
import com.wcd.boardservice.repository.CommentRepository;
import com.wcd.boardservice.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.logging.Logger;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public ResponseCommentDto createComment(RequestCommentDto requestCommentDto) {
        try {
            Comment newComment = modelMapper.map(requestCommentDto, Comment.class);
            newComment.setPost(postRepository.findById(requestCommentDto.getPostId()).orElseThrow());
            Comment savedComment = commentRepository.save(newComment);
            ResponseCommentDto responseCommentDto = modelMapper.map(savedComment, ResponseCommentDto.class);
            responseCommentDto.setPostId(savedComment.getId());
            return responseCommentDto;
        } catch (Exception e) {
            System.err.println("Error while creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseCommentDto updateComment(Long commentId, Long userId, RequestCommentDto requestCommentDto) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException());

            if (!comment.getWriterId().equals(userId)) {
                throw new Exception();
            }

            comment = modelMapper.map(requestCommentDto, Comment.class);
            Comment updatedComment = commentRepository.save(comment);
            ResponseCommentDto responseCommentDto = modelMapper.map(updatedComment, ResponseCommentDto.class);
            responseCommentDto.setPostId(updatedComment.getId());

            return responseCommentDto;
        } catch (NoSuchElementException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void deleteComment(Long commentId, Long userId) {
        try {
            Comment deleteComment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException());
            if (!deleteComment.getWriterId().equals(userId)) {
                throw new Exception();
            }
            commentRepository.delete(deleteComment);
        } catch (NoSuchElementException e) {

        } catch (Exception e) {

        }
    }

    @Override
    public ResponseCommentDto getCommentById(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException());
            ResponseCommentDto responseCommentDto = modelMapper.map(comment, ResponseCommentDto.class);

            return responseCommentDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getAllComment(Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findAll(pageable);
            Page<ResponseCommentDto> responseCommentDtos = commentLists.map(comment -> modelMapper.map(comment, ResponseCommentDto.class));
            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getAllPostComment(Long postId, Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findBypostId(postId, pageable);
            Page<ResponseCommentDto> responseCommentDtos = commentLists.map(comment -> modelMapper.map(comment, ResponseCommentDto.class));
            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<ResponseCommentDto> getALlUserComment(Long userId, Pageable pageable) {
        try {
            Page<Comment> commentLists = commentRepository.findBywriterId(userId, pageable);
            Page<ResponseCommentDto> responseCommentDtos = commentLists.map(comment -> modelMapper.map(comment, ResponseCommentDto.class));

            return responseCommentDtos;
        } catch (Exception e) {
            return null;
        }
    }

//    @Override
//    public Page<CommentDto> getAllClubComment(Long clubId, Pageable pageable) {
//        try {
//            Page<Comment> commentList= commentRepository.findByclubId(clubId, pageable);
//            List<CommentDto> commentDtoList = commentList.stream()
//                    .map(comment -> modelMapper.map(comment, CommentDto.class))
//                    .collect(Collectors.toList());
//
//            return new PageImpl<>(commentDtoList, pageable, commentList.getTotalElements());
//        } catch (Exception e) {
//            return null;
//        }
//    }
}