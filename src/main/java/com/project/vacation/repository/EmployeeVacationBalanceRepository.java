package com.project.vacation.repository;

import com.project.vacation.entity.EmployeeVacationBalance;
import com.project.vacation.entity.EmployeeVacationBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeVacationBalanceRepository extends JpaRepository<EmployeeVacationBalance, EmployeeVacationBalanceId> {
    List<EmployeeVacationBalance> findByEmployeeId(Long empId);
}
