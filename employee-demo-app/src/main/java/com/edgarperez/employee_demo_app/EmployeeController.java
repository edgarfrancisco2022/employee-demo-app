package com.edgarperez.employee_demo_app;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Employee Demo App",
        description = "Employee CRUD REST APIs"
)
@RestController
@RequestMapping("/api")
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
    }
    )
    @GetMapping("/get")
    public ResponseEntity<ResponseDto> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("Employees retrieved successfully", employeeList));
    }


    @Operation(
            summary = "Get Employee by id",
            description = "REST API to fetch Employee by id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    }
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDto> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("Employee retrieved successfully", employee));
    }


    @Operation(
            summary = "Create Employee",
            description = "REST API to create new Employee"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("Employee created successfully", savedEmployee));
    }


    @Operation(
            summary = "Update Employee by id",
            description = "REST API to update existing Employee"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDto> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("Employee updated successfully", updatedEmployee));
    }


    @Operation(
            summary = "Delete Employee by id",
            description = "REST API to delete Employee entry"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "HTTP Status NO_CONTENT"
            )
    }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(new ResponseDto("Employee deleted successfully", null));
    }
}
