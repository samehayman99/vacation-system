package com.project.vacation.controller;

import com.project.vacation.dto.VacationTypeDTO;
import com.project.vacation.service.VacationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacation-types")
@RequiredArgsConstructor
public class VacationTypeController {

    private final VacationTypeService vacationTypeService;

    @GetMapping
    public List<VacationTypeDTO> getAll() {
        return vacationTypeService.getAll();
    }

    @GetMapping("/{id}")
    public VacationTypeDTO getById(@PathVariable Long id) {
        return vacationTypeService.getById(id);
    }

    @PostMapping
    public VacationTypeDTO create(@RequestBody VacationTypeDTO dto) {
        return vacationTypeService.create(dto);
    }

    @PutMapping("/{id}")
    public VacationTypeDTO update(@PathVariable Long id, @RequestBody VacationTypeDTO dto) {
        return vacationTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vacationTypeService.delete(id);
    }
}
