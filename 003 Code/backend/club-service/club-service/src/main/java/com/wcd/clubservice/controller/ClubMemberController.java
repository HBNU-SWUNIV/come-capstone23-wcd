package com.wcd.clubservice.controller;

import com.wcd.clubservice.dto.club.response.ResponseClubMemberIdsByClubId;
import com.wcd.clubservice.dto.clubMember.response.ResponseClubMembersByClubId;
import com.wcd.clubservice.dto.clubMember.request.RequestJoinClubMember;
import com.wcd.clubservice.service.member.ClubMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "모임 멤버 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ClubMemberController {
    private final ClubMemberService clubMemberService;

    @Operation(summary = "모임 가입", description = "모임 가입")
    @PostMapping("/member")
    public ResponseEntity<Long> createClubMember(@Valid @RequestBody RequestJoinClubMember requestJoinClubMember) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clubMemberService.createClubMember(requestJoinClubMember));
    }

    @Operation(summary = "모임 멤버 조회", description = "club-id에 해당하는 모임 멤버 조회")
    @GetMapping("/member/club/{club-id}")
    public ResponseEntity<ResponseClubMembersByClubId> getClubMembers(@PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubMemberService.getClubMembers(clubId));
    }

    @Operation(summary = "모임 멤버 아이디 조회", description = "club-id에 해당하는 모임 멤버 아이디 조회")
    @GetMapping("/member/id/club/{club-id}")
    public ResponseEntity<ResponseClubMemberIdsByClubId> getClubMemberIds(@PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubMemberService.getClubMemberIds(clubId));
    }

    @Operation(summary = "모임 멤버 삭제", description = "club-id, user-id에 해당하는 모임 멤버 삭제")
    @DeleteMapping("/member/{user-id}/club/{club-id}")
    public ResponseEntity<Void> deleteClubMember(@PathVariable("user-id") Long userId, @PathVariable("club-id") Long clubId) {
        clubMemberService.deleteClubMember(userId, clubId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "모임 멤버 삭제", description = "user-id에 해당하는 모임 멤버 삭제")
    @DeleteMapping("/member/{user-id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("user-id") Long userId) {
        clubMemberService.deleteMember(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/{user-id}/club/{club-id}")
    public boolean isUserMemberOfClub(@PathVariable("club-id") Long clubId,@PathVariable("user-id") Long userId) {
        // 해당 모임의 유저인지 판단하는 로직
        return clubMemberService.isUserMemberOfClub(clubId, userId);
    }
}
