package com.edgarperez.employee_demo_app.repository;

import com.edgarperez.employee_demo_app.model.Employee;
import com.edgarperez.employee_demo_app.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByName(String name);
    Optional<Employee> findByEmail(String email);
}
