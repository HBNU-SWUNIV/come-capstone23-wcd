package com.wcd.boardservice.repository;

import com.wcd.boardservice.entity.VoteItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteItemRepository extends JpaRepository<VoteItem, Long> {
    public List<VoteItem> findByvoteId(Long voteId);
    public List<VoteItem> findByIdIn(Long[] itemIds);
}
