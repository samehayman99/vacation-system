package com.project.vacation.controller;

import com.project.vacation.dto.VacationRequestCreate;
import com.project.vacation.dto.VacationRequestResponse;
import com.project.vacation.dto.VacationRequestStatusUpdate;
import com.project.vacation.service.VacationRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacation-requests")
@RequiredArgsConstructor
public class VacationRequestController {

    private final VacationRequestService vacationRequestService;

    @GetMapping
    public List<VacationRequestResponse> getAll() {
        return vacationRequestService.getAll();
    }

    @GetMapping("/employee/{empId}")
    public List<VacationRequestResponse> getByEmployee(@PathVariable Long empId) {
        return vacationRequestService.getByEmployee(empId);
    }

    @PostMapping
    public VacationRequestResponse create(@Valid @RequestBody VacationRequestCreate dto) {
        return vacationRequestService.create(dto);
    }

    @PatchMapping("/{id}/status")
    public VacationRequestResponse updateStatus(@PathVariable Long id, @Valid @RequestBody VacationRequestStatusUpdate dto) {
        return vacationRequestService.updateStatus(id, dto);
    }
}
