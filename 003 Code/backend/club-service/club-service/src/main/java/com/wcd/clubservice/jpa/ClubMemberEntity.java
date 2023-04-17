package com.wcd.clubservice.jpa;

import com.wcd.clubservice.enums.Grade;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "club_member")
public class ClubMemberEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity club;

    @Column(nullable = false, unique = true)
    private Long userId;

    private boolean isOnline;

    @Column(nullable = false)
    private Grade grade;

    @Column(nullable = false)
    private boolean isApproval;

    @Column(nullable = false)
    private LocalDateTime joinDate;
}
