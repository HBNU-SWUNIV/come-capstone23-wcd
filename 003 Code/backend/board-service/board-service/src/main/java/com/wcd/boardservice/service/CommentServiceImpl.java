package com.wcd.boardservice.service;

import com.wcd.boardservice.dto.CommentDto;
import com.wcd.boardservice.entity.Comment;
import com.wcd.boardservice.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class CommentServiceImpl implements CommentService {
    CommentRepository commentRepository;
    ModelMapper modelMapper;


    @Override
    public CommentDto createComment(CommentDto commentDto) {
        try {
            Comment newComment = modelMapper.map(commentDto, Comment.class);
            Comment saveComment = commentRepository.save(newComment);
            CommentDto saveCommentDto = modelMapper.map(saveComment, CommentDto.class);

            return saveCommentDto;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CommentDto updateComment(Long commentId, Long userId, CommentDto commentDto) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException());

            if (!comment.getWriterId().equals(userId)) {
                throw new Exception();
            }

            comment = modelMapper.map(commentDto, Comment.class);
            Comment updateComment = commentRepository.save(comment);
            CommentDto updateCommentDto = modelMapper.map(updateComment, CommentDto.class);

            return updateCommentDto;
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
    public CommentDto getCommentById(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException());
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

            return commentDto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public Page<CommentDto> getAllComment(Pageable pageable) {
        try {
            Page<Comment> commentList= commentRepository.findAll(pageable);
            List<CommentDto> commentDtoList = commentList.stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(commentDtoList, pageable, commentList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<CommentDto> getAllPostComment(Long postId, Pageable pageable) {
        try {
            Page<Comment> commentList= commentRepository.findBypostId(postId, pageable);
            List<CommentDto> commentDtoList = commentList.stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(commentDtoList, pageable, commentList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<CommentDto> getALlUserComment(Long userId, Pageable pageable) {
        try {
            Page<Comment> commentList= commentRepository.findByuserId(userId, pageable);
            List<CommentDto> commentDtoList = commentList.stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(commentDtoList, pageable, commentList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Page<CommentDto> getAllClubComment(Long clubId, Pageable pageable) {
        try {
            Page<Comment> commentList= commentRepository.findByclubId(clubId, pageable);
            List<CommentDto> commentDtoList = commentList.stream()
                    .map(comment -> modelMapper.map(comment, CommentDto.class))
                    .collect(Collectors.toList());

            return new PageImpl<>(commentDtoList, pageable, commentList.getTotalElements());
        } catch (Exception e) {
            return null;
        }
    }
}
