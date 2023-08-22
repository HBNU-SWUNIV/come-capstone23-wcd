package com.wcd.clubservice.service.club;

import com.wcd.clubservice.client.UserServiceClient;
import com.wcd.clubservice.dto.RequestUserNamesDto;
import com.wcd.clubservice.dto.ResponseUserNamesDto;
import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.request.RequestClub;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import com.wcd.clubservice.dto.club.response.ResponseClubByClubId;
import com.wcd.clubservice.dto.club.response.ResponseClubsByUserId;
import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.enums.Grade;
import com.wcd.clubservice.file.FileStore;
import com.wcd.clubservice.repository.member.ClubMemberRepository;
import com.wcd.clubservice.repository.club.ClubRepository;
import com.wcd.clubservice.dto.club.request.RequestUpdateClub;
import com.wcd.clubservice.service.member.ClubMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubServiceImpl implements ClubService {
    private final ClubMemberService clubMemberService;
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final UserServiceClient userServiceClient;
    private final FileStore fileStore;

    // 모임 조회
    @Override
    public Page<ResponseClub> getClubs(ClubSearchCondition clubSearchCondition, Pageable pageable) {
        return clubRepository.getClubs(clubSearchCondition, pageable);
    }

    // 모임 생성
    @Transactional
    @Override
    public Long createClub(Long hostId, RequestClub requestClub) {
        String mainImageUrl = null;

        try {
            if(requestClub.getMultipartFile() != null && !requestClub.getMultipartFile().isEmpty()) {
                mainImageUrl = fileStore.storeFile(requestClub.getMultipartFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long savedClubId = clubRepository.save(requestClub.toEntity(hostId, mainImageUrl)).getId();

        // *** 모임 생성 시 모임원에 host 추가할 필요 있음 ***
        clubMemberService.createClubMember(savedClubId, hostId);
        clubMemberService.setClubMemberGrade(savedClubId, hostId, Grade.HOST);

        return savedClubId;
    }

    // user가 가입한 모임 조회
    @Override
    public List<ResponseClub> getClubsByUserId(Long userId) {
        List<Club> clubs =  clubMemberRepository.findClubByUserId(userId);
        List<ResponseClub> responseClubs = getMemberListWithWriterNames(clubs);
        return responseClubs;
    }

    // 모임 상세 조회 (club-id)
    @Override
    public ResponseClub getClubByClubId(Long clubId) {
        Club club = clubRepository.findById(clubId)
                        .orElseThrow(() -> new NoSuchElementException("Club not found with id" + clubId));
        ResponseClub responseClub = club.toResponseClub(userServiceClient.getUserNameById(club.getHostId()));
        return responseClub;
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

    // clubMembers에 작성자 명을 더한 리스트를 반환하는 메서드
    private List<ResponseClub> getMemberListWithWriterNames (List<Club> clubs) {
        // Collect userIds from post lists.
        List<Long> writerIds = clubs.stream()
                .map(Club::getHostId)
                .distinct()
                .collect(Collectors.toList());

        // Call userServiceClient to get user names.
        Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

        // Convert each post to ResponsePostListDto and replace userId with userName.
        List<ResponseClub> responseClubs = clubs.stream().map(club -> {
            ResponseClub dto = club.toResponseClub(writerIdToNameMap.get(club.getHostId()));
            return dto;
        }).collect(Collectors.toList());

        return responseClubs;
    }

    // userIds에 해당하는 UserName을 가져오는 메서드
    private Map<Long, String> getUserNames(List userIds) {
        RequestUserNamesDto requestUserNamesDto = new RequestUserNamesDto(userIds);
        ResponseUserNamesDto responseUserNamesDto = new ResponseUserNamesDto();
        responseUserNamesDto.setUserNames(userServiceClient.getUserNames(requestUserNamesDto).getUserNames());

        Map<Long, String> writerIdToNameMap = responseUserNamesDto.getUserNames().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Long.parseLong(entry.getKey()),
                        Map.Entry::getValue
                ));

        return writerIdToNameMap;
    }
}
