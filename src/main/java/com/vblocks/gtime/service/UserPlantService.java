package com.vblocks.gtime.service;

import com.vblocks.gtime.dto.userPlant.UserPlantInput;
import com.vblocks.gtime.entity.plant.Plant;
import com.vblocks.gtime.entity.User;
import com.vblocks.gtime.entity.UserPlant;
import com.vblocks.gtime.repository.PlantRepository;
import com.vblocks.gtime.repository.UserPlantRepository;
import com.vblocks.gtime.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class UserPlantService {

    private UserPlantRepository userPlantRepository;
    private PlantRepository plantRepository;
    private UserRepository userRepository;

    public UserPlant createUserPlant(UserPlantInput userPlantInput, String userUsername) {
        Plant plant = plantRepository.findById(userPlantInput.getPlantType()).orElse(null);
        if (plant == null) return null;
        User user = userRepository.findByUsername(userUsername).orElse(null);
        UserPlant userPlant = new UserPlant();
        userPlant.setUser(user);
        userPlant.setName(userPlantInput.getName());
        userPlant.setPlantType(plant);
        userPlant.setLastFertilized(LocalDate.now());
        userPlant.setLastWatered(LocalDate.now());
        userPlant.setLatestImage(plant.getImage());

        return userPlantRepository.save(userPlant);
    }

    public List<UserPlant> findAllByUserId(Long userId) {
        return userPlantRepository.findAllByUser_Id(userId);
    }

    public List<UserPlant> findAllByUserUsername(String username) {
        return userPlantRepository.findAllByUser_Username(username);
    }
}
