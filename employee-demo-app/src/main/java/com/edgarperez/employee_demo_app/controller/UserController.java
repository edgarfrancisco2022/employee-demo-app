package com.edgarperez.employee_demo_app.controller;

import com.edgarperez.employee_demo_app.dto.AuthRequest;
import com.edgarperez.employee_demo_app.dto.ResponseDTO;
import com.edgarperez.employee_demo_app.model.UserInfo;
import com.edgarperez.employee_demo_app.service.JwtService;
import com.edgarperez.employee_demo_app.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "User Controller",
        description = "User creation, JWT generation, security tests"
)
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(
            summary = "Welcome endpoint",
            description = "REST API to test non-secured endpoint"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @Operation(
            summary = "Add new user",
            description = "REST API create new User"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD_REQUEST"
            )
    })
    @PostMapping("/addNewUser")
    public ResponseEntity<ResponseDTO> addNewUser(@Valid @RequestBody UserInfo userInfo) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO(service.addUser(userInfo), null));
    }

    @Operation(
            summary = "Welcome user endpoint",
            description = "REST API to test ROLE_USER authority"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ResponseDTO> userProfile() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO("Welcome to USER profile", null));
    }

    @Operation(
            summary = "Welcome admin endpoint",
            description = "REST API to test ROLE_ADMIN authority"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            )
    })
    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> adminProfile() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDTO("Welcome to ADMIN profile", null));
    }

    @Operation(
            summary = "Generate JWT",
            description = "REST API to generate Jason Web Token"
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
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}