package com.evertonmota.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Pedido;
import com.evertonmota.cursomc.repositories.PedidoRepository;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	//Instânciar o repositório 
	@Autowired
	private PedidoRepository repo;
	
	
	public Pedido find( Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Pedido.class.getName())  );
	}
}
