package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.vote.ResponseVoteDto;
import com.wcd.boardservice.dto.vote.VoteDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponsePostDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private String category;
    private Long clubId;
    private Long writerId;
    private String writerName;
    private String title;
    private String content;
    private ResponseVoteDto responseVoteDto;

    @Builder
    public ResponsePostDto(Long id, LocalDateTime createdAt, LocalDateTime updateAt, String category, Long clubId, Long writerId, String writerName, String title, String content, ResponseVoteDto responseVoteDto) {
        this.id = id;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.category = category;
        this.clubId = clubId;
        this.writerId = writerId;
        this.writerName = writerName;
        this.title = title;
        this.content = content;
        this.responseVoteDto = responseVoteDto;
    }
}
