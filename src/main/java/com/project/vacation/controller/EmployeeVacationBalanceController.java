package com.project.vacation.controller;

import com.project.vacation.dto.EmployeeVacationSummaryResponse;
import com.project.vacation.service.EmployeeVacationBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeVacationBalanceController {

    private final EmployeeVacationBalanceService balanceService;

    @GetMapping("/{empId}/vacation-summary")
    public ResponseEntity<EmployeeVacationSummaryResponse> getVacationSummary(@PathVariable Long empId){

        return ResponseEntity.ok(balanceService.getSummaryForEmployee(empId));
    }
}
