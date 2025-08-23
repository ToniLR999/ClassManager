package com.tonilr.ClassManager.Controller;

import com.tonilr.ClassManager.Service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordResetController {

	@Autowired
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
		super();
		this.passwordResetService = passwordResetService;
	}

	@PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        passwordResetService.initiatePasswordReset(email);
        return ResponseEntity.ok("Se ha enviado un correo para restablecer tu contraseña");
    }
}