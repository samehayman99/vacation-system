package com.project.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VacationBalanceSummaryItem {
    private Long vacTypeId;
    private String vacationTypeName;
    private Integer daysRemaining;
    private Integer maxDaysPerYear;
}