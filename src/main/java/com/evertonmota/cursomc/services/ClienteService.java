package com.evertonmota.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.repositories.ClienteRepository;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	//Instânciar o repositório 
	@Autowired
	private ClienteRepository repo;
	
	
	public Cliente buscar( Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Cliente.class.getName())  );
	}
}
