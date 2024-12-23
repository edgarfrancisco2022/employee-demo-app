package com.edgarperez.employee_demo_app.controller;

import com.edgarperez.employee_demo_app.service.EmployeeService;
import com.edgarperez.employee_demo_app.dto.ResponseDTO;
import com.edgarperez.employee_demo_app.model.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Employee Controller",
        description = "Employee CRUD REST APIs"
)
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Operation(
            summary = "Get all employees",
            description = "REST API to fetch all Employee entries"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @GetMapping()
    public ResponseEntity<ResponseDTO> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO("Employees retrieved successfully", employeeList));
    }

    @Operation(
            summary = "Get Employee by id",
            description = "REST API to fetch Employee by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT_FOUND"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO("Employee retrieved successfully", employee));
    }


    @Operation(
            summary = "Create Employee",
            description = "REST API to create new Employee"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD_REQUEST"
            )
    })
    @PostMapping()
    public ResponseEntity<ResponseDTO> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO("Employee created successfully", savedEmployee));
    }


    @Operation(
            summary = "Update Employee by id",
            description = "REST API to update existing Employee"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT_FOUND"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO("Employee updated successfully", updatedEmployee));
    }


    @Operation(
            summary = "Delete Employee by id",
            description = "REST API to delete Employee entry"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO_CONTENT"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT_FOUND"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseDTO("Employee deleted successfully", null));
    }
}
