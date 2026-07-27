package com.project.vacation.service;

import com.project.vacation.dto.EmployeeVacationBalanceResponse;
import com.project.vacation.entity.Employee;
import com.project.vacation.entity.EmployeeVacationBalance;
import com.project.vacation.entity.EmployeeVacationBalanceId;
import com.project.vacation.entity.VacationType;
import com.project.vacation.repository.EmployeeVacationBalanceRepository;
import com.project.vacation.repository.VacationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeVacationBalanceService {

    final private EmployeeVacationBalanceRepository employeeVacationBalanceRepository;
    private final VacationTypeRepository vacationTypeRepository;
    public List<EmployeeVacationBalanceResponse> getBalancesForEmployee(Long empId){
        List<EmployeeVacationBalance> evb = employeeVacationBalanceRepository.findByEmployeeId(empId);
        List<EmployeeVacationBalanceResponse> result = new ArrayList<>();

        for(EmployeeVacationBalance empvacbalance : evb){
            result.add(toResponse(empvacbalance));
        }

        return result;
    }

    public void initializeBalancesForEmployee(Employee employee) {
        List<VacationType> types = vacationTypeRepository.findAll();

        for(VacationType v : types){

            EmployeeVacationBalanceId employeeVacationBalanceId = new EmployeeVacationBalanceId(employee.getId(), v.getId());

            EmployeeVacationBalance employeeVacationBalance = new EmployeeVacationBalance();
            employeeVacationBalance.setId(employeeVacationBalanceId);
            employeeVacationBalance.setEmployee(employee);
            employeeVacationBalance.setVacationType(v);
            employeeVacationBalance.setDaysRemaining(v.getDaysPerYear());

            employeeVacationBalanceRepository.save(employeeVacationBalance);
        }
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
