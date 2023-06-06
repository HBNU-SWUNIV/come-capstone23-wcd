package com.wcd.clubservice.controller;

import com.wcd.clubservice.dto.clubMember.response.ResponseClubMembersByClubId;
import com.wcd.clubservice.dto.clubMember.request.RequestJoinClubMember;
import com.wcd.clubservice.service.member.ClubMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ClubMemberController {
    private final ClubMemberService clubMemberService;

    // 모임 가입
    @PostMapping("/member")
    public ResponseEntity<Long> createClubMember(@Valid @RequestBody RequestJoinClubMember requestJoinClubMember) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clubMemberService.createClubMember(requestJoinClubMember));
    }

    // 모임 멤버 조회
    @GetMapping("/member/club/{club-id}")
    public ResponseEntity<ResponseClubMembersByClubId> getClubMembers(@PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubMemberService.getClubMembers(clubId));
    }

    // 모임 탈퇴
    @DeleteMapping("/member/{user-id}/club/{club-id}")
    public ResponseEntity<Void> deleteClubMember(@PathVariable("user-id") Long userId, @PathVariable("club-id") Long clubId) {
        clubMemberService.deleteClubMember(userId, clubId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/member/{user-id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("user-id") Long userId) {
        clubMemberService.deleteMember(userId);
        return ResponseEntity.noContent().build();
    }
}
