package com.wcd.boardservice.dto.comment;

import com.wcd.boardservice.entity.Comment;
import com.wcd.boardservice.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCommentDto {
    private String content;
    private int commentStep;
    private int commentGroup;
    private int commentOrder;

    public Comment toEntity(Long clubId, Post post, Long writerId) {
        return Comment.builder()
                .clubId(clubId)
                .post(post)
                .writerId(writerId)
                .content(this.content)
                .commentStep(this.commentStep)
                .commentGroup(this.commentGroup)
                .commentOrder(this.commentOrder)
                .build();
    }
}
