package com.project.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationBalanceResponse {
    private Long empId;
    private Long vacTypeId;
    private String vacationTypeName;
    private Integer daysRemaining;
}
