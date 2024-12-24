package com.edgarperez.employee_demo_app.service;

import com.edgarperez.employee_demo_app.exception.UserAlreadyExistsException;
import com.edgarperez.employee_demo_app.model.UserInfo;
import com.edgarperez.employee_demo_app.repository.UserInfoRepository;
import com.edgarperez.employee_demo_app.security.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    //constructor injection creates circular dependencies problem
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = userInfoRepository.findByName(username);

        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserInfo userInfo) {
        checkUserExistence(userInfo);
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);
        return "User Added Successfully";
    }

    public void checkUserExistence(UserInfo userInfo) {
        if (userInfoRepository.findByEmail(userInfo.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userInfo.getEmail() + " already exists.");
        }
        if (userInfoRepository.findByName(userInfo.getName()).isPresent()) {
            throw new UserAlreadyExistsException("User with name " + userInfo.getName() + " already exists.");
        }
    }
}
