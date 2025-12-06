package com.vblocks.gtime.repository;

import com.vblocks.gtime.entity.UserPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPlantRepository extends JpaRepository<UserPlant, Long> {
    List<UserPlant> findAllByUser_Id(Long userId);
    List<UserPlant> findAllByUser_Username(String userUsername);
}
