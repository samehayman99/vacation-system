package com.project.vacation.repository;

import com.project.vacation.entity.EmployeeVacationBalance;
import com.project.vacation.entity.RequestStatus;
import com.project.vacation.entity.VacationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VacationRequestRepository extends JpaRepository<VacationRequest, Long> {

    List<VacationRequest> findByEmployeeId(Long empId);
    List<VacationRequest> findByStatus(RequestStatus status);
    List<VacationRequest> findByEmployeeIdAndStatusIn(Long empId, List<RequestStatus> statuses);
    boolean existsByEmployeeIdAndStatus(Long empId, RequestStatus status);
    Optional<VacationRequest> findFirstByEmployeeIdAndStatusNotOrderByRequestedAtDesc(Long empId, RequestStatus status);
}
