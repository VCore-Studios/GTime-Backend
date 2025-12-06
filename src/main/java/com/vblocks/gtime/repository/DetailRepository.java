package com.vblocks.gtime.repository;

import com.vblocks.gtime.entity.detail.Detail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    Boolean deleteDetailById(Long id);
}
