package com.tonilr.ClassManager.Controller;


import com.tonilr.ClassManager.DTO.AuthRequest;
import com.tonilr.ClassManager.DTO.RegisterRequest;
import com.tonilr.ClassManager.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private final AuthService authService;
	
    public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String jwt = authService.register(request);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        String token = authService.authenticate(request);
        String role = authService.getUserRole(request.getUsername()); // o como estés accediendo al rol

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", role);
        response.put("user", request.getUsername());


        return ResponseEntity.ok(response);
    }
}