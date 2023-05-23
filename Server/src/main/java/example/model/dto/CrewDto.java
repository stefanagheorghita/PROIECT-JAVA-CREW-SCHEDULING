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
public class CrewDto {

    private Long id;

    private String name;

    private int maxHours;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
