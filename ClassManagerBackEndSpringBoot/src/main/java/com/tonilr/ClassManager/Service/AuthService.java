package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.DTO.AuthRequest;
import com.tonilr.ClassManager.DTO.RegisterRequest;
import com.tonilr.ClassManager.Model.Role;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.UserRepository;
import com.tonilr.ClassManager.Util.SanitizationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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
        user.setUsername(SanitizationUtils.sanitize(request.getUsername()));
        user.setEmail(SanitizationUtils.sanitize(request.getEmail()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.PROFESSOR);

        userRepository.save(user);

        // Notificación por correo al registrar
        emailService.sendRegistrationEmail(user.getEmail(), user.getUsername());

        return jwtService.generateToken(user);
    }

    public String authenticate(AuthRequest request) {
    	String username = request.getUsername();
    	String password = request.getPassword();
    	
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jwtService.generateToken(user);
    }
    
    public String getUserRole(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return user.getRole().name(); // o como lo tengas definido
    }
}