package com.evertonmota.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.dto.ClienteDTO;
import com.evertonmota.cursomc.repositories.ClienteRepository;
import com.evertonmota.cursomc.services.exceptions.DataIntegrityException;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	//Instânciar o repositório 
	@Autowired
	private ClienteRepository repo;
	
	
	public Cliente find( Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Cliente.class.getName())  );
	}
	
	/*
	public Cliente insert( Cliente obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	*/
	
	public Cliente update(Cliente obj) {
		Cliente clienteObj = find(obj.getId());
		updateData(clienteObj , obj);
		return repo.save(clienteObj);
	}
	
	private void updateData(Cliente clienteObj, Cliente obj) {
		 clienteObj.setNome(obj.getNome());
		 clienteObj.setEmail(obj.getEmail());
	}

	public void delete (Integer id) {
		find(id);
		
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir pois o cliente possui um pedido.");
		}
	}
	
	public List<Cliente> findAll(){
		
		return repo.findAll();
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy );
		
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO( ClienteDTO objDTO) {
		
		return new Cliente ( objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
}
