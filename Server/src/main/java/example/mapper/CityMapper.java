package example.mapper;

import example.model.dto.CityDto;
import example.model.entity.City;

public class CityMapper {
    public static CityDto toDto(City city){
        return CityDto.builder()
                .id(city.getId())
                .name(city.getName())
                .countryDto(CountryMapper.toDto(city.getCountry()))
                .build();
    }

    public static City fromDto(CityDto CityDto) {
        return City.builder()
                .id(CityDto.getId())
                .name(CityDto.getName())
                .country(CountryMapper.fromDto(CityDto.getCountryDto()))
                .build();
    }
}
