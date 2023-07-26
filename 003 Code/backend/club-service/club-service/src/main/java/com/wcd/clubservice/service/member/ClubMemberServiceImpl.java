package com.wcd.clubservice.service.member;

import com.wcd.clubservice.dto.clubMember.request.RequestJoinClubMember;
import com.wcd.clubservice.dto.clubMember.response.ResponseClubMembersByClubId;
import com.wcd.clubservice.entity.Club;
import com.wcd.clubservice.entity.ClubMember;
import com.wcd.clubservice.repository.club.ClubRepository;
import com.wcd.clubservice.repository.member.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ClubMemberServiceImpl implements ClubMemberService {
    private final ClubRepository clubRepository;
    private final ClubMemberRepository clubMemberRepository;

    @Transactional
    @Override
    public Long createClubMember(RequestJoinClubMember requestJoinClubMember) {
        Club club = clubRepository.findById(requestJoinClubMember.getClubId())
                .orElseThrow(() -> new NoSuchElementException("Club not found with id" + requestJoinClubMember.getClubId()));
        return clubMemberRepository.save(requestJoinClubMember.joinClubMember(club)).getId();
    }

    @Override
    public ResponseClubMembersByClubId getClubMembers(Long clubId) {
        List<ClubMember> clubMemberEntityList = clubMemberRepository.findByClubId(clubId);

        return ResponseClubMembersByClubId.builder().clubMemberEntityList(clubMemberEntityList).build();
    }

    @Transactional
    @Override
    public void deleteClubMember(Long userId, Long clubId) {
        ClubMember clubMember = clubMemberRepository.findByUserIdAndClubId(userId, clubId)
                .orElseThrow(() -> new NoSuchElementException("ClubMember not found with userId and clubId" + userId + "and" + clubId));

        clubMemberRepository.delete(clubMember);
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
}
