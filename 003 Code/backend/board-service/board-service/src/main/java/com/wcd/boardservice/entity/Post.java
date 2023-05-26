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

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public void changeTitle(String newTitle) {
        this.title = newTitle;
    }

    public void changeContent(String newContent) {
        this.content = newContent;
    }
    public void changeVote(Vote newVote) {
        this.vote = newVote;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
