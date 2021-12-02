package com.evertonmota.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.repositories.ClienteRepository;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

public class AuthService {

	@Autowired 
	private ClienteRepository clienteRepository;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado.");
		}
		//Criando uma nova senha para o usuário.
		String newPassword = newPassword();

		cliente.setEmail(bcrypt.encode(newPassword));
		
		clienteRepository.save(cliente);
		
		//Envio email para o cliente.
		emailService.sendNewPasswordEmail(cliente, newPassword);
		
	}

	private String newPassword() {

		char []  vet = new char[10];
		
		for(int i=0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String (vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		
		if(opt == 0) { // gerar um digito
			return (char) 	(random.nextInt(10) + 48);
		}else if(opt ==1 ) { // gera letra maiscula
			return (char) (random.nextInt(26) + 65);
		}else { //gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
