package com.project.vacation.service;

import com.project.vacation.dto.VacationTypeDTO;
import com.project.vacation.entity.VacationType;
import com.project.vacation.exception.ResourceNotFoundException;
import com.project.vacation.repository.VacationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VacationTypeService {

    private final VacationTypeRepository vacationTypeRepository;

    public List<VacationTypeDTO> getAll(){
        List<VacationType> types = vacationTypeRepository.findAll();
        List<VacationTypeDTO> results = new ArrayList<>();

        for(VacationType vc : types){
            results.add(toDTO(vc));
        }
        return results;
    }

    public VacationTypeDTO getById(Long id){
        VacationType vacationType = findEntityById(id);

        return toDTO(vacationType);

    }

    public VacationTypeDTO create(VacationTypeDTO vacationTypeDTO){
        VacationType type = new VacationType();
        type.setName(vacationTypeDTO.getName());
        type.setDaysPerYear(vacationTypeDTO.getDaysPerYear());
        return toDTO(vacationTypeRepository.save(type));
    }

    public void delete(Long id) {
        VacationType type = findEntityById(id);
        vacationTypeRepository.delete(type);
    }

    public VacationTypeDTO update(Long id, VacationTypeDTO dto) {
        VacationType type = findEntityById(id);
        type.setName(dto.getName());
        type.setDaysPerYear(dto.getDaysPerYear());
        return toDTO(vacationTypeRepository.save(type));
    }


    private VacationType findEntityById(Long id) {
        return vacationTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacation type not found: " + id));
    }

    private VacationTypeDTO toDTO(VacationType type) {
        return new VacationTypeDTO(type.getId(), type.getName(), type.getDaysPerYear());
    }
}
