package com.edgarperez.employee_demo_app.service;

import com.edgarperez.employee_demo_app.model.Employee;
import com.edgarperez.employee_demo_app.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeService service;

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = List.of(
                new Employee(1L, "John", "john@example.com", 30, 50000.0),
                new Employee(2L, "Peter", "peter@example.com", 20, 20000.0));
        when(repository.findAll()).thenReturn(employees);

        List<Employee> result = service.getAllEmployees();

        assertEquals(employees, result);
        verify(repository).findAll();
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setName("John");
        when(repository.findById(eq(1L))).thenReturn(Optional.of(employee));

        Employee result = service.getEmployeeById(1L);

        assertEquals("John", result.getName());
        verify(repository).findById(eq(1L));
    }

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee();
        employee.setName("John");
        when(repository.save(employee)).thenReturn(employee);

        Employee result = service.createEmployee(employee);

        assertEquals("John", result.getName());
        verify(repository).save(employee);
    }

    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setName("John");

        when(repository.findById(1L)).thenReturn(Optional.of(employee));
        when(repository.save(any(Employee.class))).thenReturn(employee);

        Employee result = service.updateEmployee(1L, employee);

        assertEquals("John", result.getName());
        verify(repository).findById(1L);
        verify(repository).save(employee);
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(repository).deleteById(1L);
        when(repository.existsById(any())).thenReturn(true);
        service.deleteEmployee(1L);
        verify(repository).deleteById(eq(1L));
    }
}
