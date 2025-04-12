package com.tonilr.ClassManager.Controller;


import com.tonilr.ClassManager.DTO.RegisterRequest;
import com.tonilr.ClassManager.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        String jwt = authService.authenticate(username, password);
        return ResponseEntity.ok(jwt);
    }
}