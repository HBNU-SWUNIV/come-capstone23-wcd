package com.wcd.clubservice.service.club;

import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.request.RequestClub;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import com.wcd.clubservice.dto.club.response.ResponseClubByClubId;
import com.wcd.clubservice.dto.club.response.ResponseClubsByUserId;
import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.file.FileStore;
import com.wcd.clubservice.repository.member.ClubMemberRepository;
import com.wcd.clubservice.repository.club.ClubRepository;
import com.wcd.clubservice.dto.club.request.RequestUpdateClub;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService{
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FileStore fileStore;

    // 모임 조회
    @Override
    public Page<ResponseClub> getClubs(ClubSearchCondition clubSearchCondition, Pageable pageable) {
        return clubRepository.getClubs(clubSearchCondition, pageable);
    }

    // 모임 생성
    @Transactional
    @Override
    public Long createClub(Long hostId,RequestClub requestClub) {
        String mainImageUrl = null;

        try {
            mainImageUrl = fileStore.storeFile(requestClub.getMultipartFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return clubRepository.save(requestClub.toEntity(hostId, mainImageUrl)).getId();
    }

    // 모임 상세 조회 (user-id)
    @Override
    public ResponseClubsByUserId getClubByUserId(Long userId) {
        List<Club> clubEntityList =  clubMemberRepository.findClubByUserId(userId);

        return ResponseClubsByUserId.builder().clubEntityList(clubEntityList).build();
    }

    // 모임 상세 조회 (club-id)
    @Override
    public ResponseClubByClubId getClubById(Long clubId) {
        Club club = clubRepository.findById(clubId)
                        .orElseThrow(() -> new NoSuchElementException("Club not found with id" + clubId));

        return ResponseClubByClubId.builder().club(club).build();
    }

    // 모임 이름 조회 (club-id)
    @Override
    public String getClubNameById(Long clubId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException("Club not found with id" + clubId));

        return club.getClubName();
    }

    // 모임 정보 수정
    @Transactional
    @Override
    public Long updateClubById(Long clubId, RequestUpdateClub requestUpdateClub) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException("Club not found with id" + clubId));

        String mainImageUrl = null;

        try {
            mainImageUrl = fileStore.storeFile(requestUpdateClub.getMultipartFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        club.updateClub(requestUpdateClub, mainImageUrl);

        return clubId;
    }

    // 모임 삭제
    @Transactional
    @Override
    public void deleteClub(Long clubId) {
        Club club = clubRepository.findById(clubId)
                        .orElseThrow(() -> new NoSuchElementException("Club not found with id" + clubId));

        clubRepository.delete(club);
    }
}
