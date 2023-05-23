package example.mapper;

import example.model.dto.CrewDto;
import example.model.entity.Crew;

public class CrewMapper {

    public static CrewDto toDto(Crew crew) {
       return CrewDto.builder()
               .id(crew.getId())
               .name(crew.getName())
               .build();
    }

    public static Crew fromDto(CrewDto crewDto) {
        return Crew.builder()
                .id(crewDto.getId())
                .name(crewDto.getName())
                .build();
    }
}
