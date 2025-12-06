package com.vblocks.gtime.repository;

import com.vblocks.gtime.entity.Detail;
import com.vblocks.gtime.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetailRepository extends JpaRepository<Detail, Long> {
    Boolean deleteDetailById(Long id);
}
