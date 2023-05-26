package com.wcd.boardservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VoteItem extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id")
    @NotNull
    private Vote vote;

    @OneToMany(mappedBy = "voteItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteRecord> voteRecords;

    @NotNull
    private String value;

    public void setVote(Vote vote) {
        this.vote = vote;
    }
}
