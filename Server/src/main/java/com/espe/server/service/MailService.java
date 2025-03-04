package com.espe.server.service;

import org.springframework.stereotype.Service;

import com.espe.server.configuration.MailManager;

@Service
public class MailService {
	
	private final MailManager mailManager;
	
	public MailService(MailManager mailManager) {
		this.mailManager = mailManager;
	}
	
	
	public void sendMessage(String email, String message) {
		mailManager.SendMessage(email, message);
	}
	
}
