package com.wcd.userservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_evaluation")
@Entity
public class UserEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    private int score;

    @Lob
    private String review;

    @Builder
    public UserEvaluation(Users user, int score, String review) {
        this.user = user;
        this.score = score;
        this.review = review;
    }
}
