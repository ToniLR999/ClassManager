package com.tonilr.ClassManager.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {


    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void sendRegistrationEmail(String to, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Bienvenido a Classroom Manager");
        message.setText("Hola " + name + ",\n\nTu cuenta ha sido creada exitosamente.\n\nSaludos,\nEl equipo de Classroom Manager");
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Recuperación de contraseña");
        message.setText("Haz clic en el siguiente enlace para restablecer tu contraseña:\n\nhttp://localhost:4200/reset-password?token=" + token);
        mailSender.send(message);
    }
}