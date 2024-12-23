package com.edgarperez.employee_demo_app.service;

import com.edgarperez.employee_demo_app.exception.EmployeeAlreadyExistsException;
import com.edgarperez.employee_demo_app.exception.ResourceNotFoundException;
import com.edgarperez.employee_demo_app.exception.UserAlreadyExistsException;
import com.edgarperez.employee_demo_app.model.Employee;
import com.edgarperez.employee_demo_app.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found with id: ";

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        checkEmployeeExistence(employee);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE_NOT_FOUND + id));
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = getEmployeeById(id);
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException(EMPLOYEE_NOT_FOUND + id);
        }
        employeeRepository.deleteById(id);
    }

    public void checkEmployeeExistence(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee with email " + employee.getEmail() + " already exists.");
        }
        if (employeeRepository.findByName(employee.getName()).isPresent()) {
            throw new EmployeeAlreadyExistsException("Employee with name " + employee.getName() + " already exists.");
        }
    }
}
