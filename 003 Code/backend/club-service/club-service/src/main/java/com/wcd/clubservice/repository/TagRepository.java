package com.wcd.clubservice.repository;

import com.wcd.clubservice.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
}
