package com.project.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationSummaryResponse {
    private Long empId;
    private String employeeName;
    private List<VacationBalanceSummaryItem> balances;
}
