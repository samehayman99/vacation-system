package com.project.vacation.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationTypeDTO {
    private Long id;
    private String name;
    private int daysPerYear;
}
