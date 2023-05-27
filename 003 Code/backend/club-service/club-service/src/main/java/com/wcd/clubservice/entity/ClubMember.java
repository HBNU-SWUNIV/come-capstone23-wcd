package com.wcd.clubservice.entity;

import com.wcd.clubservice.enums.ApprovalMethod;
import com.wcd.clubservice.enums.Grade;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ClubMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(nullable = false, unique = true)
    private Long userId;

    private boolean isOnline;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Grade grade;

    @Column(nullable = false)
    private boolean isApproval;

    @Column(nullable = false)
    private LocalDateTime joinDate;

    @Builder
    public ClubMember(Club club, Long userId, boolean isOnline, Grade grade, boolean isApproval, LocalDateTime joinDate) {
        this.club = club;
        this.userId = userId;
        this.isOnline = isOnline;
        this.grade = grade;
        this.isApproval = isApproval;
        this.joinDate = joinDate;
    }

    public void changeClub(Club club) {
        this.club = club;
    }


    @Override
    public String toString() {
        return "ClubMember{" +
                "id=" + id +
                ", club=" + club +
                ", userId=" + userId +
                ", isOnline=" + isOnline +
                ", grade=" + grade +
                ", isApproval=" + isApproval +
                ", joinDate=" + joinDate +
                '}';
    }
}
