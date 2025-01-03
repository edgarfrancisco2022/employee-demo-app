package com.edgarperez.employee_demo_app.repository;

import com.edgarperez.employee_demo_app.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String name);
    Optional<UserInfo> findByEmail(String email);
}