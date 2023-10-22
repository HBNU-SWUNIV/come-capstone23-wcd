package com.wcd.clubservice.service.member;

import com.wcd.clubservice.client.AlarmServiceClient;
import com.wcd.clubservice.dto.club.response.ResponseClubMemberIdsByClubId;
import com.wcd.clubservice.client.UserServiceClient;
import com.wcd.clubservice.dto.RequestUserNamesDto;
import com.wcd.clubservice.dto.ResponseUserNamesDto;
import com.wcd.clubservice.dto.clubMember.response.ResponseClubMember;
import com.wcd.clubservice.dto.feignclient.RequestJoinClub;
import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.ClubMember;
import com.wcd.clubservice.enums.ApprovalMethod;
import com.wcd.clubservice.enums.Grade;
import com.wcd.clubservice.exception.ClubMemberNotFoundException;
import com.wcd.clubservice.exception.ClubNotFoundException;
import com.wcd.clubservice.exception.UserAlreadyJoinedClubException;
import com.wcd.clubservice.repository.club.ClubRepository;
import com.wcd.clubservice.repository.member.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final AlarmServiceClient alarmServiceClient;
    private final UserServiceClient userServiceClient;

    @Transactional
    @Override
    public Long createClubMember(Long clubId, Long userId) {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ClubNotFoundException("Club not found with id" + clubId));

        if(clubMemberRepository.existsByClubIdAndUserId(clubId, userId)) {
            throw new UserAlreadyJoinedClubException("User " + userId + " has already joined club " + clubId);
        }

        Boolean isApproval = false;

        if(club.getApprovalMethod().equals(ApprovalMethod.FREE)) {
            isApproval = true;
        }

        ClubMember clubMember = ClubMember.builder()
            .club(club)
            .userId(userId)
            .grade(Grade.BRONZE)
            .joinDate(LocalDateTime.now())
            .isApproval(isApproval)
            .build();

        alarmServiceClient.notifyJoinClub(RequestJoinClub.builder()
                .clubId(clubId)
                .userId(userId)
                .build());

        return clubMemberRepository.save(clubMember).getId();
    }

    @Override
    public List<ResponseClubMember> getClubMembers(Long clubId) {
        List<ClubMember> clubMembers = clubMemberRepository.findByClubId(clubId);
        List<ResponseClubMember> responseClubMembers = getMemberListWithWriterNames(clubMembers);
        return responseClubMembers;
    }

    @Override
    public ResponseClubMemberIdsByClubId getClubMemberIds(Long clubId) {
        List<ClubMember> clubMemberEntityList = clubMemberRepository.findByClubId(clubId);

        return ResponseClubMemberIdsByClubId.builder().clubMemberEntityList(clubMemberEntityList).build();
    }

    @Transactional
    @Override
    public void deleteClubMember(Long userId, Long clubId) {
        ClubMember clubMember = clubMemberRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new ClubMemberNotFoundException("ClubMember not found with userId and clubId" + userId + "and" + clubId));

        clubMemberRepository.delete(clubMember);
    }

    @Override
    public Long setClubMemberGrade(Long clubId, Long userId, Grade grade) {
        ClubMember clubMember = clubMemberRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new ClubMemberNotFoundException("ClubMember not found with userId and clubId" + userId + "and" + clubId));
        clubMember.changeGrade(grade);
        return clubMember.getUserId();
    }

    @Transactional
    @Override
    public void deleteMember(Long userId) {
        if(clubMemberRepository.existsByUserId(userId)) {
            clubMemberRepository.deleteByUserId(userId);
        }
    }

    @Override
    public boolean isUserMemberOfClub(Long clubId, Long userId) {
        if(clubMemberRepository.existsByClubIdAndUserId(clubId, userId)) {
            return true;
        } else {
            return false;
        }
    }

    // clubMembers에 작성자 명을 더한 리스트를 반환하는 메서드
    private List<ResponseClubMember> getMemberListWithWriterNames (List<ClubMember> clubMembers) {
        // Collect userIds from post lists.
        List<Long> writerIds = clubMembers.stream()
                .map(ClubMember::getUserId)
                .distinct()
                .collect(Collectors.toList());

        // Call userServiceClient to get user names.
        Map<Long, String> writerIdToNameMap = getUserNames(writerIds);

        // Convert each post to ResponsePostListDto and replace userId with userName.
        List<ResponseClubMember> responseMemberListDtos = clubMembers.stream().map(clubMember -> {
            ResponseClubMember dto = clubMember.toResponseClubMember(writerIdToNameMap.get(clubMember.getUserId()));
            return dto;
        }).collect(Collectors.toList());

        return responseMemberListDtos;
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
