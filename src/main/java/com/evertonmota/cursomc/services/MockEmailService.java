package com.evertonmota.cursomc.services;

import javax.mail.internet.MimeMessage;

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

	@Override
	public void sendHtmlEmail(MimeMessage sm) {
		LOG.info("Simulando o envio de E-mail HTML...");
		LOG.info(sm.toString());
		LOG.info("E-mail enviado com sucesso!");		
	}

}
