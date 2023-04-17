package com.wcd.clubservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wcd.clubservice.enums.ApprovalMethod;
import com.wcd.clubservice.enums.Grade;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMember {

    private Long userId;
    private boolean isOnline;
    private Grade grade;
    private boolean isApproval;
    private LocalDateTime joinDate;
}
