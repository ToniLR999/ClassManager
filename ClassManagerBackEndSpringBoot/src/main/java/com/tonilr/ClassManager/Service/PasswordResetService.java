package com.tonilr.ClassManager.Service;

import com.tonilr.ClassManager.Model.PasswordResetToken;
import com.tonilr.ClassManager.Model.User;
import com.tonilr.ClassManager.Repository.PasswordResetTokenRepository;
import com.tonilr.ClassManager.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

	@Autowired
    private final UserRepository userRepository;

	@Autowired
	private final PasswordResetTokenRepository tokenRepository;
    
	@Autowired
	private final EmailService emailService;

	public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository,
			EmailService emailService) {
		super();
		this.userRepository = userRepository;
		this.tokenRepository = tokenRepository;
		this.emailService = emailService;
	}


	public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);
        emailService.sendPasswordResetEmail(email, token);
    }

    // método para validar y aplicar nuevo password se implementará luego
}