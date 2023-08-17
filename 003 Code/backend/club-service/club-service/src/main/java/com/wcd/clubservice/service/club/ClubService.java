package com.wcd.clubservice.service.club;

import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.request.RequestClub;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import com.wcd.clubservice.dto.club.response.ResponseClubByClubId;
import com.wcd.clubservice.dto.club.response.ResponseClubsByUserId;
import com.wcd.clubservice.dto.club.request.RequestUpdateClub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubService {
    Page<ResponseClub> getClubs(ClubSearchCondition clubSearchCondition, Pageable pageable);

    Long createClub(Long hostId, RequestClub requestClub);

    ResponseClubByClubId getClubById(Long clubId);

    Long updateClubById(Long clubId, RequestUpdateClub requestUpdateClub);

    void deleteClub(Long clubId);

    ResponseClubsByUserId getClubByUserId(Long userId);

    String getClubNameById(Long clubId);
}
