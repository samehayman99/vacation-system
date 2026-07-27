package com.project.vacation.service;

import com.project.vacation.dto.VacationRequestResponse;
import com.project.vacation.entity.VacationRequest;
import com.project.vacation.repository.VacationRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationRequestService {

    private final VacationRequestRepository vacationRequestRepository;


    public List<VacationRequestResponse> getByEmployee(Long empId){
        List<VacationRequest> requests = vacationRequestRepository.findByEmployeeId(empId);
        List<VacationRequestResponse> results = new ArrayList<>();
        for(VacationRequest vacationRequest : requests){
            results.add(toResponse(vacationRequest));
        }

        return results;
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
