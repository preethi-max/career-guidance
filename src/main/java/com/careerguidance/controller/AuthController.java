package com.careerguidance.controller;

import com.careerguidance.dto.ApiResponse;
import com.careerguidance.dto.LoginRequest;
import com.careerguidance.dto.RegisterRequest;
import com.careerguidance.dto.UserDto;
import com.careerguidance.entity.User;
import com.careerguidance.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(@Valid @RequestBody RegisterRequest req,
                                                         HttpServletRequest request,
                                                         HttpServletResponse response) {
        try {
            UserDto userDto = authService.register(req, request, response);
            return ResponseEntity.ok(ApiResponse.ok("Registration successful! Welcome to Career Guidance.", userDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().body(ApiResponse.error("Registration failed: " + ex.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@Valid @RequestBody LoginRequest req,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        try {
            UserDto userDto = authService.login(req, request, response);
            return ResponseEntity.ok(ApiResponse.ok("Login successful!", userDto));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid email or password. Please verify your credentials."));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDto>> getCurrentUser() {
        Optional<User> userOpt = authService.getCurrentAuthenticatedUser();
        if (userOpt.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error("Not authenticated"));
        }
        UserDto dto = authService.buildUserDto(userOpt.get());
        return ResponseEntity.ok(ApiResponse.ok(dto));
    }
}
