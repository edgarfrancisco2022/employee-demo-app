package com.edgarperez.employee_demo_app.controller;

import com.edgarperez.employee_demo_app.model.Employee;
import com.edgarperez.employee_demo_app.security.JwtAuthFilter;
import com.edgarperez.employee_demo_app.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(
        value = EmployeeController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = JwtAuthFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockitoBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetAllEmployees() throws Exception {
        //Given
        List<Employee> employees = List.of(
                new Employee(1L, "John", "john@example.com", 30, 50000.0),
                new Employee(2L, "Peter", "peter@example.com", 20, 20000.0));
        //When
        when(employeeService.getAllEmployees()).thenReturn(employees);
        //Then
        mockMvc.perform(get("/api/employee"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Map.of("data", employees))));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void testGetEmployeeById() throws Exception {
        //Given
        Employee employee = new Employee(1L, "John", "john@example.com", 30, 50000.0);
        //When
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);
        //
        mockMvc.perform(get("/api/employee/1"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Map.of("data", employee))));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void testCreateEmployee() throws Exception {
        //Given
        Employee employee = new Employee(1L, "John", "john@example.com", 30, 50000.0);
        //When
        when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);
        //Then
        mockMvc.perform(post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Map.of("data", employee))));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void testUpdateEmployee() throws Exception {
        //Given
        Employee employee = new Employee(1L, "John", "john@example.com", 30, 50000.0);
        //When
        when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(employee);
        //Then
        mockMvc.perform(put("/api/employee/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee))
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(Map.of("data", employee))));
    }

    @Test
    @WithMockUser(username = "user", password = "password")
    void testDeleteEmployee() throws Exception {
        //When
        doNothing().when(employeeService).deleteEmployee(eq(1L));
        //Then
        mockMvc.perform(delete("/api/employee/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message", is("Employee deleted successfully")));
    }
}

