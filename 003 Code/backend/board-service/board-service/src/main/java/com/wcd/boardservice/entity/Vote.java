package com.wcd.boardservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vote")
public class Vote extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "writer_id")
    private Long writerId;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VoteItem> voteItems;

    @OneToMany(mappedBy = "vote", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VoteRecord> voteRecords;

    private LocalDateTime deadline;

    @Column(name = "is_multiple_selection")
    private Boolean isMultipleSelection;

    @Column(name = "is_blind")
    private Boolean isBlind;

    @Column(name = "is_add_item")
    private boolean isAddItem;

    public void setPost(Post post) {
        this.post = post;
    }

    public void changeVoteItem(List<VoteItem> voteItems) {
        this.voteItems = voteItems;
    }
}
