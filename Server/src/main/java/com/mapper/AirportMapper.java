package com.mapper;

import com.model.dto.AirportDto;
import com.model.entity.Airport;

public class AirportMapper {
    public static AirportDto toDto(Airport airport){
        return AirportDto.builder()
                .id(airport.getId())
                .name(airport.getName())
                .cityDto(CityMapper.toDto(airport.getCity()))
                .build();
    }

    public static Airport fromDto(AirportDto airportDto) {
        return Airport.builder()
                .id(airportDto.getId())
                .name(airportDto.getName())
                .city(CityMapper.fromDto(airportDto.getCityDto()))
                .build();
    }
}
