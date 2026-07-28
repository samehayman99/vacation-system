package com.project.vacation.dto;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationRequestCreate {


    @NotNull
    private Long empId;

    @NotNull
    private Long vacTypeId;

    @NotNull @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

//    @NotNull @Positive
//    private Integer daysRequested;
}
