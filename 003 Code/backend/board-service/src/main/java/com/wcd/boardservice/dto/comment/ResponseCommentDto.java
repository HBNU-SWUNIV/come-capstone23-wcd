package com.wcd.boardservice.dto.comment;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseCommentDto {
    private Long id;
    private Long clubId;
    private Long postId;
    private Long writerId;
    private String writerName;
    private String content;
    private int commentStep;
    private int commentGroup;
    private int commentOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    @Builder
    public ResponseCommentDto(Long id,
                              Long clubId,
                              Long postId,
                              Long writerId,
                              String writerName,
                              String content,
                              int commentStep,
                              int commentGroup,
                              int commentOrder,
                              LocalDateTime createdAt,
                              LocalDateTime updateAt) {
        this.id = id;
        this.clubId = clubId;
        this.postId = postId;
        this.writerId = writerId;
        this.writerName = writerName;
        this.content = content;
        this.commentStep = commentStep;
        this.commentGroup = commentGroup;
        this.commentOrder = commentOrder;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
