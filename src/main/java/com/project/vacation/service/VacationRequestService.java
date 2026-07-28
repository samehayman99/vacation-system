package com.project.vacation.service;

import com.project.vacation.dto.VacationRequestCreate;
import com.project.vacation.dto.VacationRequestResponse;
import com.project.vacation.dto.VacationRequestStatusUpdate;
import com.project.vacation.entity.*;
import com.project.vacation.exception.ResourceNotFoundException;
import com.project.vacation.repository.EmployeeRepository;
import com.project.vacation.repository.EmployeeVacationBalanceRepository;
import com.project.vacation.repository.VacationRequestRepository;
import com.project.vacation.repository.VacationTypeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final VacationTypeRepository vacationTypeRepository;
    private final EmployeeVacationBalanceRepository balanceRepository;
    public List<VacationRequestResponse> getByEmployee(Long empId){
        List<VacationRequest> requests = vacationRequestRepository.findByEmployeeId(empId);
        List<VacationRequestResponse> results = new ArrayList<>();
        for(VacationRequest vacationRequest : requests){
            results.add(toResponse(vacationRequest));
        }

        return results;
    }

    public List<VacationRequestResponse> getAll() {
        List<VacationRequest> requests = vacationRequestRepository.findAll();
        List<VacationRequestResponse> results = new ArrayList<>();

        for (VacationRequest request : requests) {
            results.add(toResponse(request));
        }

        return results;
    }

    public VacationRequestResponse create(VacationRequestCreate dto){

        boolean hasPending = vacationRequestRepository.existsByEmployeeIdAndStatus(dto.getEmpId(), RequestStatus.PENDING);
        if (hasPending) {
            throw new IllegalStateException("You already have a pending vacation request");
        }

        Employee employee = employeeRepository.findById(dto.getEmpId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmpId()));

        VacationType type = vacationTypeRepository.findById(dto.getVacTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacation type not found: " + dto.getVacTypeId()));

        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Optional<VacationRequest> lastRequest = vacationRequestRepository
                .findFirstByEmployeeIdAndStatusNotOrderByRequestedAtDesc(dto.getEmpId(), RequestStatus.CANCELLED);

        if (lastRequest.isPresent()) {
            VacationRequest existing = lastRequest.get();
            boolean overlaps = dto.getStartDate().isBefore(existing.getEndDate().plusDays(1))
                    && existing.getStartDate().isBefore(dto.getEndDate().plusDays(1));

            if (overlaps) {
                throw new IllegalStateException("You already have a request overlapping these dates");
            }
        }

        long daysBetween = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;

        VacationRequest vacationRequest = new VacationRequest();
        vacationRequest.setEmployee(employee);
        vacationRequest.setVacationType(type);
        vacationRequest.setEndDate(dto.getEndDate());
        vacationRequest.setStartDate(dto.getStartDate());
        vacationRequest.setDaysRequested((int) daysBetween);
        vacationRequest.setStatus(RequestStatus.PENDING);

        return toResponse(vacationRequestRepository.save(vacationRequest));
    }

    @Transactional
    public VacationRequestResponse updateStatus(Long requestId, VacationRequestStatusUpdate dto){
        VacationRequest request = findEntityById(requestId);


        if (dto.getStatus() == RequestStatus.APPROVED && request.getStatus() != RequestStatus.APPROVED) {
            deductBalance(request);
        }

        request.setStatus(dto.getStatus());
        return toResponse(vacationRequestRepository.save(request));


    }

    @Transactional
    public VacationRequestResponse cancel(Long requestId){
        VacationRequest request = findEntityById(requestId);

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new IllegalStateException("Only pending requests can be cancelled");
        }

        request.setStatus(RequestStatus.CANCELLED);
        return toResponse(vacationRequestRepository.save(request));
    }

    private void deductBalance(VacationRequest request) {
        EmployeeVacationBalanceId balanceId = new EmployeeVacationBalanceId(
                request.getEmployee().getId(),
                request.getVacationType().getId()
        );

        EmployeeVacationBalance balance = balanceRepository.findById(balanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Balance not found for this employee/type"));

        int remaining = balance.getDaysRemaining() - request.getDaysRequested();
        if (remaining < 0) {
            throw new IllegalStateException("Not enough vacation days remaining");
        }

        balance.setDaysRemaining(remaining);
        balanceRepository.save(balance);
    }
    private VacationRequest findEntityById(Long id) {
        return vacationRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacation request not found: " + id));
    }
    private VacationRequestResponse toResponse(VacationRequest request) {
        VacationRequestResponse dto = new VacationRequestResponse();
        dto.setId(request.getId());
        dto.setEmpId(request.getEmployee().getId());
        dto.setEmployeeName(request.getEmployee().getFirstName() + " " + request.getEmployee().getLastName());
        dto.setVacTypeId(request.getVacationType().getId());
        dto.setVacationTypeName(request.getVacationType().getName());
        dto.setStartDate(request.getStartDate());
        dto.setEndDate(request.getEndDate());
        dto.setDaysRequested(request.getDaysRequested());
        dto.setStatus(request.getStatus());
        dto.setRequestedAt(request.getRequestedAt());
        return dto;
    }
}
