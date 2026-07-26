package com.project.vacation.service;

import com.project.vacation.dto.EmployeeResponse;
import com.project.vacation.entity.Employee;
import com.project.vacation.exception.ResourceNotFoundException;
import com.project.vacation.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeResponse> getAll(){

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse>  result = new ArrayList<>();

        for(Employee emp : employees){
            result.add(toResponse(emp));
        }
        return result;
    }

    private EmployeeResponse toResponse(Employee emp) {

        EmployeeResponse employeeResponse = new EmployeeResponse();
        employeeResponse.setId(emp.getId());
        employeeResponse.setFirstName(emp.getFirstName());
        employeeResponse.setLastName(emp.getLastName());
        employeeResponse.setEmail(emp.getEmail());
        employeeResponse.setHireDate(emp.getHireDate());

        if(emp.getManager()!= null){
            employeeResponse.setManagerId(emp.getManager().getId());
            employeeResponse.setManagerName(emp.getManager().getFirstName() + "  " + emp.getManager().getLastName());
        }

        return employeeResponse;
    }

    public EmployeeResponse getById(Long id) {
        return toResponse(findEntityById(id));
    }

    private Employee findEntityById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

}
