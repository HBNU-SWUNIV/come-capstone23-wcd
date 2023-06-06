package com.wcd.clubservice.controller;

import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.response.ResponseClubByClubId;
import com.wcd.clubservice.dto.club.response.ResponseClubsByUserId;
import com.wcd.clubservice.service.club.ClubService;
import com.wcd.clubservice.dto.club.request.RequestClub;
import com.wcd.clubservice.dto.club.request.RequestUpdateClub;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class ClubController {
    private final Environment env;
    private final ClubService clubService;

    @GetMapping("health_check")
    public String status() {
        return String.format("It's Working in Club Service on PORT %s", env.getProperty("local.server.port"));
    }

    // 모임 조회
    @GetMapping("/club")
    public ResponseEntity<Page<ResponseClub>> getClubs(ClubSearchCondition clubSearchCondition, Pageable pageable) {
        Page<ResponseClub> clubPage = clubService.getClubs(clubSearchCondition, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(clubPage);
    }

    // 모임 생성
    @PostMapping("/club")
    public ResponseEntity<Long> createClub(@Valid @ModelAttribute RequestClub requestClub) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clubService.createClub(requestClub));
    }

    // 모임 상세 조회 (user-id)
    @GetMapping("/club/member/{user-id}")
    public ResponseEntity<ResponseClubsByUserId> getClubByUserId(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubByUserId(userId));
    }

    // 모임 상세 조회 (club-id)
    @GetMapping("/club/{club-id}")
    public ResponseEntity<ResponseClubByClubId> getClubById(@PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubById(clubId));
    }

    // 모임 정보 수정
    @PutMapping("/club/{club-id}")
    public ResponseEntity<Long> updateClub(@PathVariable("club-id") Long clubId, @Valid @ModelAttribute RequestUpdateClub requestUpdateClub) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.updateClubById(clubId, requestUpdateClub));
    }

    // 모임 삭제
    @DeleteMapping("/club/{club-id}")
    public ResponseEntity<Void> deleteClub(@PathVariable("club-id") Long clubId) {
        clubService.deleteClub(clubId);

        return ResponseEntity.noContent().build();
    }
}
