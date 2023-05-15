package com.wcd.boardservice;

import com.wcd.boardservice.dto.comment.CommentDto;
import com.wcd.boardservice.dto.comment.RequestCommentDto;
import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.entity.Comment;
import com.wcd.boardservice.service.CommentService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void createCommentTest() {
        Comment newComment = new Comment(null, null, 1L, "ㅇㄹㄴㅁ", 1, 1, 1);
        RequestCommentDto requestCommentDto = modelMapper.map(newComment, RequestCommentDto.class);
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCommentDto);
        CommentDto findCommentDto = commentService.getCommentById(responseCommentDto.getId());

        assertThat(responseCommentDto).isEqualTo(findCommentDto);
    }
}
