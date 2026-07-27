package com.project.vacation.service;

import com.project.vacation.dto.EmployeeVacationBalanceResponse;
import com.project.vacation.entity.EmployeeVacationBalance;
import com.project.vacation.repository.EmployeeVacationBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeVacationBalanceService {

    final private EmployeeVacationBalanceRepository employeeVacationBalanceRepository;

    public List<EmployeeVacationBalanceResponse> getBalancesForEmployee(Long empId){
        List<EmployeeVacationBalance> evb = employeeVacationBalanceRepository.findByEmployeeId(empId)
        List<EmployeeVacationBalanceResponse> result = new ArrayList<>();

        for(EmployeeVacationBalance empvacbalance : evb){
            result.add(toResponse(empvacbalance));
        }

        return result;
    }

    private EmployeeVacationBalanceResponse toResponse(EmployeeVacationBalance balance) {

        return new EmployeeVacationBalanceResponse(
                balance.getEmployee().getId(),
                balance.getVacationType().getId(),
                balance.getVacationType().getName(),
                balance.getDaysRemaining()
        );
    }
}
