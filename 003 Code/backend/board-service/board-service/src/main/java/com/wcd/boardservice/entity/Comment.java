package com.wcd.boardservice.entity;


import com.wcd.boardservice.dto.comment.ResponseCommentDto;
import com.wcd.boardservice.dto.comment.UpdateRequestCommentDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "club_id")
    private Long clubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @NotNull
    private Post post;

    @Column(name = "writer_id")
    private Long writerId;

    @NotNull
    private String content;

    @Column(name = "comment_step")
    @NotNull
    private int commentStep;

    @Column(name = "comment_group")
    @NotNull
    private int commentGroup;

    @Column(name = "comment_order")
    @NotNull
    private int commentOrder;

    @Builder
    public Comment(Long clubId, Post post, Long writerId, String content, int commentStep, int commentGroup, int commentOrder) {
        this.clubId = clubId;
        this.post = post;
        this.writerId = writerId;
        this.content = content;
        this.commentStep = commentStep;
        this.commentGroup = commentGroup;
        this.commentOrder = commentOrder;
    }

    public void update(UpdateRequestCommentDto updateRequestCommentDto) {
        this.content = updateRequestCommentDto.getContent();
    }

    public ResponseCommentDto toResponseCommentDto(String writerName) {
        return ResponseCommentDto.builder()
                .id(this.id)
                .clubId(this.id)
                .postId(this.post.getId())
                .writerId(this.writerId)
                .writerName(writerName)
                .content(this.content)
                .commentStep(this.commentStep)
                .commentGroup(this.commentGroup)
                .commentOrder(this.commentOrder)
                .createdAt(this.getCreatedAt())
                .updateAt(this.getUpdatedAt())
                .build();
    }
}
