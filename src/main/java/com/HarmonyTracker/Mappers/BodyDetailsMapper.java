package com.HarmonyTracker.Mappers;

import DTO.BodyDetailsDTO;
import com.HarmonyTracker.Entities.BodyDetails;
import com.HarmonyTracker.Entities.Enums.ActivityLevel;
import com.HarmonyTracker.Entities.Enums.GenderType;
import com.HarmonyTracker.Entities.Enums.UnitType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper
public interface BodyDetailsMapper {

    BodyDetailsMapper INSTANCE = Mappers.getMapper(BodyDetailsMapper.class);

    @Mapping(target = "genderType", source = "gender")
    @Mapping(target = "preferredUnit", source = "preferredUnit")
    @Mapping(target = "preferredWeightUnit", source = "preferredWeightUnit")
    @Mapping(target = "activityLevel", source = "activityLevel")
    BodyDetails toEntity(BodyDetailsDTO dto);

    default GenderType mapGender(String gender) {
        return GenderType.valueOf(gender); // Assuming your GenderType enum has the same names as the gender strings
    }

    default UnitType mapUnit(String unit) {
        return UnitType.valueOf(unit); // Assuming your UnitType enum has the same names as the unit strings
    }

    default ActivityLevel mapActivity(double activityLevel) {
        return ActivityLevel.getType(activityLevel);
    }
    default LocalDate map(long value) {
        return Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate(); // Assuming your ActivityLevel enum has the same names as the activity strings
    }
}