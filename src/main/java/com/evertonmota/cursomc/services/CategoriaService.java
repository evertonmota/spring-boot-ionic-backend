package com.evertonmota.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Categoria;
import com.evertonmota.cursomc.dto.CategoriaDTO;
import com.evertonmota.cursomc.repositories.CategoriaRepository;
import com.evertonmota.cursomc.services.exceptions.DataIntegrityException;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	//Instânciar o repositório 
	@Autowired
	private CategoriaRepository repo;
	
	
	public Categoria find( Integer id) {
		// Na versão 7 do java era usado 
		// ex :. Categoria cat = repository.findOne(id) caso nao exista o id , retornava um valor Null. 
		// Versão do Spring 2.x.x Java Versao 8
		// Retorna um Optional. É um objeto container que vai vai carregar o tipo que voce informar.
		// Vai encapsular o obj instanciado ou nao. 
		Optional<Categoria> obj = repo.findById(id);
		// return obj.orElse(null)
		// ou criar uma Classse
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Categoria.class.getName())  );
	}
	
	public Categoria insert( Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria catObj = find(obj.getId());
		updateData(catObj , obj);
		return repo.save(catObj);
	}
	
	private void updateData(Categoria catObj, Categoria obj) {
		catObj.setNome(obj.getNome());
	}

	public void delete (Integer id) {
		find(id);
		
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
		}
	}
	
	public List<Categoria> findAll(){
		
		return repo.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy );
		
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDTO( CategoriaDTO objDTO) {
		
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
