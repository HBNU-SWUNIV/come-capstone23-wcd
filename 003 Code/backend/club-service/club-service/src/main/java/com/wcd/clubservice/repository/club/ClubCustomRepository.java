package com.wcd.clubservice.repository.club;

import com.wcd.clubservice.dto.club.ClubSearchCondition;
import com.wcd.clubservice.dto.club.response.ResponseClub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubCustomRepository {
    Page<ResponseClub> getClubs(ClubSearchCondition condition, Pageable pageable);
}
