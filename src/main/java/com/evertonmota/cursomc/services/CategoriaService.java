package com.evertonmota.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Categoria;
import com.evertonmota.cursomc.repositories.CategoriaRepository;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	//Instânciar o repositório 
	@Autowired
	private CategoriaRepository repo;
	
	
	public Categoria buscar( Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Categoria.class.getName())  );
	}
	
	public Categoria insert( Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
}
