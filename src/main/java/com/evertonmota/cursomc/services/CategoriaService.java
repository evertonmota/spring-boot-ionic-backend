package com.evertonmota.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Categoria;
import com.evertonmota.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	//Instânciar o repositório 
	@Autowired
	private CategoriaRepository repo;
	
	
	public Categoria buscar( Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null);
	}
}
