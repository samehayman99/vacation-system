package com.project.vacation.service;

import com.project.vacation.dto.VacationRequestCreate;
import com.project.vacation.dto.VacationRequestResponse;
import com.project.vacation.entity.Employee;
import com.project.vacation.entity.RequestStatus;
import com.project.vacation.entity.VacationRequest;
import com.project.vacation.entity.VacationType;
import com.project.vacation.exception.ResourceNotFoundException;
import com.project.vacation.repository.EmployeeRepository;
import com.project.vacation.repository.VacationRequestRepository;
import com.project.vacation.repository.VacationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final VacationTypeRepository vacationTypeRepository;
    public List<VacationRequestResponse> getByEmployee(Long empId){
        List<VacationRequest> requests = vacationRequestRepository.findByEmployeeId(empId);
        List<VacationRequestResponse> results = new ArrayList<>();
        for(VacationRequest vacationRequest : requests){
            results.add(toResponse(vacationRequest));
        }

        return results;
    }

    public VacationRequestResponse Create(VacationRequestCreate dto){
        VacationRequest vacationRequest = new VacationRequest();
        Employee employee = employeeRepository.findById(dto.getEmpId()).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + dto.getEmpId()));;
        vacationRequest.setEmployee(employee);

        VacationType type = vacationTypeRepository.findById(dto.getVacTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vacation type not found: " + dto.getVacTypeId()));
        vacationRequest.setVacationType(type);
        vacationRequest.setEndDate(dto.getEndDate());
        vacationRequest.setStartDate(dto.getStartDate());
        vacationRequest.setDaysRequested(dto.getDaysRequested());
        vacationRequest.setStatus(RequestStatus.PENDING);

        return toResponse(vacationRequestRepository.save(vacationRequest));
    }

    private VacationRequest findEntityById(Long id) {
        return vacationRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacation type not found: " + id));
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
