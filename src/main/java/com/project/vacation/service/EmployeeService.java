package com.project.vacation.service;

import com.project.vacation.dto.EmployeeRequest;
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
    private final EmployeeVacationBalanceService balanceService;

    public List<EmployeeResponse> getAll(){

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponse>  result = new ArrayList<>();

        for(Employee emp : employees){
            result.add(toResponse(emp));
        }
        return result;
    }

    public EmployeeResponse getById(Long id) {
        return toResponse(findEntityById(id));
    }

    public void delete(Long id) {
        Employee employee = findEntityById(id);
        employeeRepository.delete(employee);
    }
    public EmployeeResponse update(Long id, EmployeeRequest request){

        Employee employee = findEntityById(id);

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setHireDate(request.getHireDate());

        if (request.getManagerId() != null) {
            Employee manager = findEntityById(request.getManagerId());
            employee.setManager(manager);
        } else {
            employee.setManager(null);
        }

        return toResponse(employeeRepository.save(employee));

    }

    public EmployeeResponse create (EmployeeRequest request){
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setHireDate(request.getHireDate());
        if(request.getManagerId() != null) {
            Employee manager = findEntityById(request.getManagerId());
            employee.setManager(manager);
        }
        Employee saved = employeeRepository.save(employee);
        balanceService.initializeBalancesForEmployee(saved);
        return toResponse(saved);
    }

    private Employee findEntityById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
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
}
