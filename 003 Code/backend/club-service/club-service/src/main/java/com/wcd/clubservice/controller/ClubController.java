package com.wcd.clubservice.controller;

import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.response.ResponseClubByClubId;
import com.wcd.clubservice.dto.club.response.ResponseClubsByUserId;
import com.wcd.clubservice.service.club.ClubService;
import com.wcd.clubservice.dto.club.request.RequestClub;
import com.wcd.clubservice.dto.club.request.RequestUpdateClub;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "모임 관련 API")
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

    @Operation(summary = "모임 조회", description = "모임 조회 (검색, 카테고리별 필터링 가능, 페이지네이션)")
    @Parameters({
            @Parameter(name = "search", description = "검색", example = "독서 모임"),
            @Parameter(name = "category", description = "카테고리", example = "게임"),
            @Parameter(name = "page", description = "페이지", example = "1"),
            @Parameter(name = "size", description = "크기", example = "10"),
    })
    @GetMapping("/club")
    public ResponseEntity<Page<ResponseClub>> getClubs(ClubSearchCondition clubSearchCondition, Pageable pageable) {
        Page<ResponseClub> clubPage = clubService.getClubs(clubSearchCondition, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(clubPage);
    }

    @Operation(summary = "모임 생성", description = "모임 생성")
    @PostMapping("/club")
    public ResponseEntity<Long> createClub(@RequestHeader("user-id") Long hostId,
                                           @Valid @ModelAttribute RequestClub requestClub) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clubService.createClub(hostId, requestClub));
    }

    @Operation(summary = "모임 조회", description = "user-id에 해당하는 유저의 모임 조회")
    @GetMapping("/club/member/{user-id}")
    public ResponseEntity<ResponseClubsByUserId> getClubByUserId(@PathVariable("user-id") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubByUserId(userId));
    }

    @Operation(summary = "모임 조회", description = "club-id에 해당하는 모임 조회")
    @GetMapping("/club/{club-id}")
    public ResponseEntity<ResponseClubByClubId> getClubById(@PathVariable("club-id") Long clubId) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.getClubById(clubId));
    }

    @Operation(summary = "모임 수정", description = "club-id에 해당하는 모임 수정")
    @PutMapping("/club/{club-id}")
    public ResponseEntity<Long> updateClub(@PathVariable("club-id") Long clubId, @Valid @ModelAttribute RequestUpdateClub requestUpdateClub) {
        return ResponseEntity.status(HttpStatus.OK).body(clubService.updateClubById(clubId, requestUpdateClub));
    }

    @Operation(summary = "모임 삭제", description = "club-id에 해당하는 모임 삭제")
    @DeleteMapping("/club/{club-id}")
    public ResponseEntity<Void> deleteClub(@PathVariable("club-id") Long clubId) {
        clubService.deleteClub(clubId);

        return ResponseEntity.noContent().build();
    }
}
