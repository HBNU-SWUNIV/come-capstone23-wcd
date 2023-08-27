package com.wcd.clubservice.controller;

import com.wcd.clubservice.dto.club.response.ResponseClub;
import com.wcd.clubservice.service.club.ClubService;
import com.wcd.clubservice.service.member.ClubMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "유저 모임 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserClubController {
    private final ClubService clubService;
    private final ClubMemberService clubMemberService;

    @Operation(summary = "유저의 모임 조회", description = "user-id에 해당하는 유저의 모임 조회")
    @GetMapping("/{user-id}/clubs")
    public ResponseEntity<List<ResponseClub>> getClubByUserId(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubsByUserId(userId));
    }

    @Operation(summary = "유저가 가입한 모든 모임 탈퇴", description = "user-id에 해당하는 모임 멤버 삭제")
    @DeleteMapping("/{user-id}")
    public ResponseEntity<Void> deleteMember(@PathVariable("user-id") Long userId) {
        clubMemberService.deleteMember(userId);
        return ResponseEntity.noContent().build();
    }
}
