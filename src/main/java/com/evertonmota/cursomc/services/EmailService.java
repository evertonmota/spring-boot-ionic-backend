package com.evertonmota.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage sm);
	
	void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage sm);
	
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}

