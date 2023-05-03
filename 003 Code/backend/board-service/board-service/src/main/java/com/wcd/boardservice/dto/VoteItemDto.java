package com.wcd.boardservice.dto;

import com.wcd.boardservice.entity.Vote;
import lombok.Data;

@Data
public class VoteItemDto {
    private Long id;
    private Vote vote;
    private String item;
}
