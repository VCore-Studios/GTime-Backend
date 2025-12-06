package com.vblocks.gtime.entity;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.vblocks.gtime.entity.plant.Plant;
import com.vblocks.gtime.entity.plant.WateringFrequency;

@DgsComponent
public class UserPlantCareDataFetcher {

    // This method is called whenever 'currentWateringSchedule' is requested on a UserPlant object
    @DgsData(parentType = "UserPlant", field = "growthStage")
    public WateringFrequency getCurrentScheduleForUserPlant(DgsDataFetchingEnvironment dfe) {
        // The source object is the UserPlant instance currently being resolved
        UserPlant userPlant = dfe.getSource();

        // 1. Get the dynamic stage from the UserPlant
        GrowthStage dynamicStage = userPlant.getGrowthStage();

        // 2. Access the static template (Plant) data linked to the UserPlant
        // Assuming the PlantTemplate is linked as 'plantTemplate' (as defined previously)
        Plant template = userPlant.getPlantType();

        // 3. Search the template's defined schedules (WateringFrequency collection)
        //    to find the one matching the current dynamic stage.
        return template.getWateringFrequencies().stream()
                .filter(freq -> freq.getGrowthStage() == dynamicStage)
                .findFirst()
                .orElse(null);
    }
}