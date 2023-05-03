package com.wcd.boardservice.repository;

import com.wcd.boardservice.entity.VoteRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRecordRepository extends JpaRepository<VoteRecord, Long> {
    public List<VoteRecord> findByvoteIdAnduserId(Long voteId, Long userId);
    public void deleteByvoteIdAnduserId(Long voteId, Long userId);
}
