package com.wcd.boardservice.repository;

import com.wcd.boardservice.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findBypostId(Long postId, Pageable pageable);
//    Page<Comment> findByclubId(Long clubId, Pageable pageable);
    Page<Comment> findBywriterId(Long writerId, Pageable pageable);
}
