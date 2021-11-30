package com.evertonmota.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{

	@Autowired
	private MailSender emailSender;
	
	@Autowired
	private JavaMailSender sender;
	
	private static final Logger LOG =  LoggerFactory.getLogger(SmtpEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage sm) {
	
		LOG.info("Simulando o envio de E-mail...");
		emailSender.send(sm);
		LOG.info("E-mail enviado com sucesso!");
		
	}

	@Override
	public void sendHtmlEmail(MimeMessage mime) {
		LOG.info("Simulando o envio de E-mail HTML...");
		sender.send(mime);
		LOG.info("E-mail enviado com sucesso!");
		
	}

}
