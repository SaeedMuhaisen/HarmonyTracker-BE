package com.HarmonyTracker.Mappers;

import DTO.BodyDetailsDTO;
import com.HarmonyTracker.Entities.BodyDetails;
import com.HarmonyTracker.Entities.Enums.ActivityLevel;
import com.HarmonyTracker.Entities.Enums.GenderType;
import com.HarmonyTracker.Entities.Enums.UnitType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Mapper
public interface BodyDetailsMapper {

    BodyDetailsMapper INSTANCE = Mappers.getMapper(BodyDetailsMapper.class);

    @Mapping(target = "gender", source = "genderType", qualifiedByName = "mapGenderToString")
    @Mapping(target = "preferredUnit", source = "preferredUnit", qualifiedByName = "mapUnitToString")
    @Mapping(target = "preferredWeightUnit", source = "preferredWeightUnit", qualifiedByName = "mapUnitToString")
    @Mapping(target = "activityLevel", source = "activityLevel", qualifiedByName = "mapActivityToDouble")
    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "mapLocalDateToLong")
    BodyDetailsDTO toDTO(BodyDetails entity);

    @Mapping(target = "genderType", source = "gender", qualifiedByName = "mapStringToGender")
    @Mapping(target = "preferredUnit", source = "preferredUnit", qualifiedByName = "mapStringToUnit")
    @Mapping(target = "preferredWeightUnit", source = "preferredWeightUnit", qualifiedByName = "mapStringToUnit")
    @Mapping(target = "activityLevel", source = "activityLevel", qualifiedByName = "mapDoubleToActivity")
    @Mapping(target = "birthDate", source = "birthDate", qualifiedByName = "mapLongToLocalDate")
    BodyDetails toEntity(BodyDetailsDTO dto);

    @Named("mapGenderToString")
    default String mapGenderToString(GenderType genderType) {
        return genderType != null ? genderType.name() : null;
    }

    @Named("mapUnitToString")
    default String mapUnitToString(UnitType unitType) {
        return unitType != null ? unitType.name() : null;
    }

    @Named("mapActivityToDouble")
    default double mapActivityToDouble(ActivityLevel activityLevel) {
        return activityLevel != null ? activityLevel.getValue() : 0.0;
    }

    @Named("mapLocalDateToLong")
    default long mapLocalDateToLong(LocalDate value) {
        return value != null ? value.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() : 0L;
    }

    @Named("mapStringToGender")
    default GenderType mapStringToGender(String gender) {
        return GenderType.valueOf(gender);
    }

    @Named("mapStringToUnit")
    default UnitType mapStringToUnit(String unit) {
        return UnitType.valueOf(unit);
    }

    @Named("mapDoubleToActivity")
    default ActivityLevel mapDoubleToActivity(double activityLevel) {
        return ActivityLevel.getType(activityLevel);
    }

    @Named("mapLongToLocalDate")
    default LocalDate mapLongToLocalDate(long value) {
        return Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}