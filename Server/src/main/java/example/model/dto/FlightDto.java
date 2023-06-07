package example.model.dto;

import example.model.entity.Airplane;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDto {
    private int id;
    private DayOfWeek departureDay;
    private CityDto departureCityDto;
    private CityDto arrivalCityDto;
    private LocalTime arrivalTime;

    private LocalTime departureTime;

    private String departureHour;

    private String arrivalHour;
    private Airplane airplane;
    private Integer employeesNo;

   // private Integer pilotId;


}
