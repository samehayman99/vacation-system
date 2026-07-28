package com.project.vacation.repository;

import com.project.vacation.entity.EmployeeVacationBalance;
import com.project.vacation.entity.RequestStatus;
import com.project.vacation.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    List<VacationRequest> findByEmployeeId(Long empId);
    List<VacationRequest> findByStatus(RequestStatus status);
    List<VacationRequest> findByEmployeeIdAndStatusIn(Long empId, List<RequestStatus> statuses);
}
