package example.mapper;

import example.model.dto.CountryDto;
import example.model.entity.Country;

public class CountryMapper {

    public static CountryDto toDto(Country country) {
       return CountryDto.builder()
               .id(country.getId())
               .name(country.getName())
               .build();
    }

    public static Country fromDto(CountryDto countryDto) {
        return Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .build();
    }
}
