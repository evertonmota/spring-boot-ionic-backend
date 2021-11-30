package com.evertonmota.cursomc.services;

import org.springframework.mail.SimpleMailMessage;
import com.evertonmota.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage sm);
}
