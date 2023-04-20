package com.wcd.clubservice.service;

import com.wcd.clubservice.dto.ClubDto;
import com.wcd.clubservice.dto.ClubMemberDto;
import com.wcd.clubservice.entity.ClubEntity;
import com.wcd.clubservice.entity.ClubMemberEntity;
import com.wcd.clubservice.entity.TagEntity;
import com.wcd.clubservice.enums.ApprovalMethod;
import com.wcd.clubservice.enums.Grade;
import com.wcd.clubservice.repository.ClubMemberRepository;
import com.wcd.clubservice.repository.ClubRepository;
import com.wcd.clubservice.vo.RequestUpdateClub;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClubServiceImpl implements ClubService{
    ClubRepository clubRepository;
    ClubMemberRepository clubMemberRepository;

    public ClubServiceImpl(ClubRepository clubRepository, ClubMemberRepository clubMemberRepository) {
        this.clubRepository = clubRepository;
        this.clubMemberRepository = clubMemberRepository;
    }

    // 모임 조회
    @Override
    public Iterable<ClubEntity> getClub() {
        Iterable<ClubEntity> clubList = clubRepository.findAll();

        if (!clubList.iterator().hasNext()) {
            throw new NoSuchElementException();
        }

        return clubList;
    }

    // 모임 생성
    @Override
    public ClubDto createClub(ClubDto clubDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ClubEntity clubEntity = mapper.map(clubDto, ClubEntity.class);
        clubEntity.setRecruitment(true);

        List<TagEntity> tagEntityList = new ArrayList<>();
        for (String tag: clubDto.getTags()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setClub(clubEntity);
            tagEntity.setTag(tag);

            tagEntityList.add(tagEntity);
        }
        clubEntity.setTagList(tagEntityList);
        clubEntity.setCreatedAt(LocalDateTime.now());

        clubRepository.save(clubEntity);

        ClubDto returnClubDto = mapper.map(clubEntity, ClubDto.class);

        returnClubDto.setTags(clubDto.getTags());

        return returnClubDto;
    }

    // 모임 상세 조회 (user-id)
    @Override
    public Iterable<ClubEntity> getClubByUserId(Long userId) {
        Iterable<ClubEntity> clubList = clubMemberRepository.findClubByUserId(userId);

        if (!clubList.iterator().hasNext()) {
            throw new NoSuchElementException();
        }

        return clubList;
    }

    // 모임 상세 조회 (club-id)
    @Override
    public ClubDto getClubById(Long clubId) {
        ClubEntity clubEntity = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException());

        ClubDto clubDto = new ModelMapper().map(clubEntity, ClubDto.class);

        return clubDto;
    }

    // 모임 정보 수정
    @Override
    public ClubDto updateClubById(Long clubId, RequestUpdateClub requestUpdateClub) {
        ClubEntity clubEntity = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException());

        clubEntity.setClubName(requestUpdateClub.getClubName());
        clubEntity.setCategory(requestUpdateClub.getCategory());
        clubEntity.setDescription(requestUpdateClub.getDescription());
        clubEntity.setMainImageUrl(requestUpdateClub.getMainImageUrl());
        clubEntity.setRecruitment(requestUpdateClub.isRecruitment());

        ClubEntity newClub = clubRepository.save(clubEntity);

        ClubDto clubDto = new ModelMapper().map(newClub, ClubDto.class);

        return clubDto;
    }

    // 모임 삭제
    @Override
    public void deleteClub(Long clubId) {
        ClubEntity clubEntity = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException());

        clubRepository.delete(clubEntity);
    }

    // 모임 멤버 조회
    @Override
    public Iterable<ClubMemberEntity> getClubMember(Long clubId) {
        Iterable<ClubMemberEntity> clubMemberList = clubMemberRepository.findByClubId(clubId);

        if (!clubMemberList.iterator().hasNext()) {
            throw new NoSuchElementException();
        }
        return clubMemberList;
    }

    // 모임 가입
    @Override
    public ClubMemberDto createClubMember(Long clubId, Long userId) {
        ClubEntity clubEntity = clubRepository.findById(clubId)
                .orElseThrow(() -> new NoSuchElementException());

        ClubMemberEntity clubMemberEntity = new ClubMemberEntity();
        clubMemberEntity.setClub(clubEntity);
        clubMemberEntity.setUserId(userId);
        clubMemberEntity.setGrade(Grade.BRONZE);
        clubMemberEntity.setJoinDate(LocalDateTime.now());

        if (clubEntity.getApprovalMethod().equals(ApprovalMethod.FREE)) {
            clubMemberEntity.setApproval(true);
        } else {
            clubMemberEntity.setApproval(false);
        }

        clubMemberRepository.save(clubMemberEntity);

        ClubMemberDto clubMemberDto = new ModelMapper().map(clubMemberEntity, ClubMemberDto.class);

        return clubMemberDto;
    }

    // 모임 탈퇴
    @Override
    public void deleteClubMember(Long clubId, Long userId) {
        clubMemberRepository.deleteByClubIdAndUserId(clubId, userId);
    }
}
