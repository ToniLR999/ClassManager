package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.Model.PasswordResetToken;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.PasswordResetTokenRepository;
import com.tonilr.ClassManager.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    	private final UserRepository userRepository;

	private final PasswordResetTokenRepository tokenRepository;
    
	private final EmailService emailService;
	
	private final PasswordEncoder passwordEncoder;

	public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository,
			EmailService emailService, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
	}

	public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        
        // ✅ Buscar token existente para el usuario
        PasswordResetToken resetToken = tokenRepository.findByUser(user)
                .orElse(new PasswordResetToken());
        
        // ✅ Actualizar o crear nuevo token
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(email, token);
    }
    
    public void resetPassword(String token, String newPassword) {
        // Buscar el token en la base de datos
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token de reset no válido"));
        
        // Verificar que el token no haya expirado
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token de reset ha expirado");
        }
        
        // Obtener el usuario asociado al token
        User user = resetToken.getUser();
        
        // Encriptar la nueva contraseña
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        
        // Guardar la nueva contraseña del usuario
        userRepository.save(user);
        
        // Eliminar el token usado
        tokenRepository.delete(resetToken);
    }
}