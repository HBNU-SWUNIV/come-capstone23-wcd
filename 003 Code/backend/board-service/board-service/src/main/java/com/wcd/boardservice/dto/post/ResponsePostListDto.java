package com.wcd.boardservice.dto.post;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponsePostListDto {
    private Long id;
    private String category;
    private Long clubId;
    private Long writerId;
    private String writerName;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Long voteId;

    @Builder
    public ResponsePostListDto(Long id, String category, Long clubId, Long writerId, String writerName, String title, LocalDateTime createdAt, LocalDateTime updateAt, Long voteId) {
        this.id = id;
        this.category = category;
        this.clubId = clubId;
        this.writerId = writerId;
        this.writerName = writerName;
        this.title = title;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.voteId = voteId;
    }
}
