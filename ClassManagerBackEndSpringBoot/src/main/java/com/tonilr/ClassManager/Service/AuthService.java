package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.DTO.RegisterRequest;
import com.tonilr.ClassManager.Model.Role;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.UserRepository;
import com.tonilr.ClassManager.Security.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
    private final UserRepository userRepository;

	@Autowired
	private final PasswordEncoder passwordEncoder;
   
	@Autowired
	private final JwtService jwtService;
    
	@Autowired
	private final AuthenticationManager authenticationManager;
    
	@Autowired
	private final EmailService emailService;

	
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authenticationManager, EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.emailService = emailService;
	}

	public String register(RegisterRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PROFESSOR);

        userRepository.save(user);

        // Notificación por correo al registrar
        emailService.sendRegistrationEmail(user.getEmail(), user.getUsername());

        return jwtService.generateToken(user);
    }

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }
}