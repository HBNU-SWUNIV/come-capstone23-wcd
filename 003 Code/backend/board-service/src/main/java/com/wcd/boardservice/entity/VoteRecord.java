package com.wcd.boardservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class VoteRecord extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_record_id")
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    @NotNull
    private Vote vote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_item_id")
    @NotNull
    private VoteItem voteItem;

    public VoteRecord(Long userId, Vote vote, VoteItem voteItem) {
        this.userId = userId;
        this.vote = vote;
        this.voteItem = voteItem;
    }
}
