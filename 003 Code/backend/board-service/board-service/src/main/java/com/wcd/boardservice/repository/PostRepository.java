package com.wcd.boardservice.repository;

import com.wcd.boardservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByclubId(Long clubId, Pageable pageable);
    Page<Post> findByuserId(Long userId, Pageable pageable);
}
