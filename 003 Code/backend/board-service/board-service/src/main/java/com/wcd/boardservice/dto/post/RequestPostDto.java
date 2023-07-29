package com.wcd.boardservice.dto.post;

import com.wcd.boardservice.dto.vote.RequestVoteDto;
import com.wcd.boardservice.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPostDto {
    private String category;
    private String title;
    private String content;
    private RequestVoteDto requestVoteDto;

    public Post toEntity(Long clubId, Long writerId) {
        return Post.builder()
                .category(this.category)
                .clubId(clubId)
                .writerId(writerId)
                .title(this.title)
                .content(this.content)
                .vote(null)
                .build();
    }
}
