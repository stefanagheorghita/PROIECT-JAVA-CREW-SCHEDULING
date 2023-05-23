package com.mapper;

import com.model.dto.AirportDto;
import com.model.dto.CityDto;
import com.model.dto.FlightDto;
import com.model.entity.City;
import com.model.entity.Flight;

public class FlightMapper {
    public static FlightDto toDto(Flight flight){
        return FlightDto.builder()
                .id(flight.getId())
                .destination(flight.getDestination())
                .departureCityDto(CityMapper.toDto(flight.getDepartureCity()))
                .arrivalCityDto(CityMapper.toDto(flight.getArrivalCity()))
                .arrivalDate(flight.getArrivalDate())
                .airplaneId(flight.getAirplaneId())
                .employeesNo(flight.getEmployeesNo())
                .build();
    }

    public static FlightDto fromDto(FlightDto flightDto){
        return Flight.builder()
                .id(flightDto.getId())
                .destination(flightDto.getDestination())
                .departureCity(CityMapper.fromDto(flightDto.getDepartureCityDto()))
                .arrivalCity(CityMapper.fromDto(flightDto.getArrivalCityDto()))
                .arrivalDate(flightDto.getArrivalDate())
                .airplaneId(flightDto.getAirplaneId())
                .employeesNo(flightDto.getEmployeesNo())
                .build();
    }
}
