package com.wcd.boardservice.entity;

import jakarta.persistence.*;
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

    private String category;

    private Long clubId;

    private Long writerId;

    private String title;

    private String content;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Vote vote;
}
