package example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlightDto {
    private Long id;
    private String destination;
    private CityDto departureCityDto;
    private CityDto arrivalCityDto;
    private Timestamp arrivalDate;
    private Integer airplaneId;
    private Integer employeesNo;



}
