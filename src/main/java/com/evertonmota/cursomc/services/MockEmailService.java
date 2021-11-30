package com.evertonmota.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService{

	private static final Logger LOG =  LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage sm) {

		LOG.info("Simulando o envio de E-mail...");
		LOG.info(sm.toString());
		LOG.info("E-mail enviado com sucesso!");
		
	}

}
