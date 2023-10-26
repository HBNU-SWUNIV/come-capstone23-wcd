package com.wcd.boardservice.repository;

import com.wcd.boardservice.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Page<Post> findAll(Specification<Post> spec, Pageable pageable);
    Page<Post> findByClubId(Long clubId, Specification<Post> spec, Pageable pageable);
    Page<Post> findByWriterId(Long writerId, Pageable pageable);
    Page<Post> findByClubIdAndWriterId(Long clubId, Long writerId, Pageable pageable);
}
