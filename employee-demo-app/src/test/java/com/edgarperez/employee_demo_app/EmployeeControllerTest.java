package com.edgarperez.employee_demo_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    @WithMockUser(username = "user", password = "password")
    void testGetAllEmployees() throws Exception {
        //Given
        List<Employee> employees = List.of(
                new Employee(1L, "John", "john@example.com", 30, 50000.0),
                new Employee(2L, "Peter", "peter@example.com", 20, 20000.0));
        //When
        when(employeeService.getAllEmployees()).thenReturn(employees);
        //
        mockMvc.perform(get("/api/get"))
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
        mockMvc.perform(get("/api/get/1"))
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
        mockMvc.perform(post("/api/create")
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
        mockMvc.perform(put("/api/update/1")
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
        mockMvc.perform(delete("/api/delete/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message", is("Employee deleted successfully")));
    }
}

