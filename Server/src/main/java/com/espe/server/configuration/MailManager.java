package com.espe.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class MailManager {

	@Value("${spring.mail.username}")
	private String sender;
	
	
	private final JavaMailSender javaMailSender;
	
	public MailManager(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void SendMessage(String email, String messageEmail) {
		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
			message.setSubject("Correo de Recuperación de Contraseña Seguros ESPE");
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(email);
			helper.setText(messageEmail);
			helper.setFrom(sender);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
		
	}
}
