package example.mapper;

import example.model.dto.AirportDto;
import example.model.dto.CityDto;
import example.model.dto.FlightDto;
import example.model.entity.City;
import example.model.entity.Flight;

public class FlightMapper {
    public static FlightDto toDto(Flight flight){
        return FlightDto.builder()
                .id(flight.getId())
                .departureCityDto(CityMapper.toDto(flight.getDepartureCity()))
                .arrivalCityDto(CityMapper.toDto(flight.getArrivalCity()))
                .arrivalTime(flight.getArrivalTime())
                .departureTime(flight.getDepartureTime())
                .departureDay(flight.getDepartureDay())
                .airplane(flight.getAirplane())
                .employeesNo(flight.getEmployeesNo())
                .build();
    }

    public static Flight fromDto(FlightDto flightDto){
        return Flight.builder()
                .id(flightDto.getId())
                .departureCity(CityMapper.fromDto(flightDto.getDepartureCityDto()))
                .arrivalCity(CityMapper.fromDto(flightDto.getArrivalCityDto()))
                .arrivalTime(flightDto.getArrivalTime())
                .airplane(flightDto.getAirplane())
                .employeesNo(flightDto.getEmployeesNo())
                .build();
    }
}
