package com.wcd.boardservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

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

    public void setPost(Post post) {
        this.post = post;
    }
}
