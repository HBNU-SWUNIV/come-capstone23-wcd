package com.wcd.boardservice.entity;

import com.wcd.boardservice.dto.post.ResponsePostDto;
import com.wcd.boardservice.dto.post.ResponsePostListDto;
import com.wcd.boardservice.dto.post.UpdateRequestPostDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post extends BaseEntity{

    public Post(String category, Long clubId, Long writerId, String title, String content) {
        this.category = category;
        this.clubId = clubId;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull
    private String category;

    @Column(name = "club_id")
    @NotNull
    private Long clubId;

    @Column(name = "writer_id")
    private Long writerId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Vote vote;

    @Builder
    public Post(String category, Long clubId, Long writerId, String title, String content, Vote vote) {
        this.category = category;
        this.clubId = clubId;
        this.writerId = writerId;
        this.title = title;
        this.content = content;
        this.vote = vote;
    }

    public void update(UpdateRequestPostDto updateRequestPostDto) {
        this.category = updateRequestPostDto.getCategory();
        this.title = updateRequestPostDto.getTitle();
        this.content = updateRequestPostDto.getContent();
        this.vote = null;
    }

    public ResponsePostDto toResponsePostDto(String writerName) {
        return ResponsePostDto.builder()
                .id(this.id)
                .category(this.category)
                .clubId(this.clubId)
                .writerId(this.writerId)
                .writerName(writerName)
                .title(this.title)
                .content(this.content)
                .responseVoteDto(null)
                .createdAt(this.getCreatedAt())
                .updateAt(this.getUpdatedAt())
                .build();
    }

    public ResponsePostListDto toResponsePostListDto(String writerName) {
        return ResponsePostListDto.builder()
                .id(this.id)
                .category(this.category)
                .clubId(this.clubId)
                .writerId(this.writerId)
                .writerName(writerName)
                .title(this.title)
                .voteId(null)
                .createdAt(this.getCreatedAt())
                .updateAt(this.getUpdatedAt())
                .build();
    }
}
