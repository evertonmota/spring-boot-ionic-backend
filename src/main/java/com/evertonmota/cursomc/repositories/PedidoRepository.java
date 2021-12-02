package com.evertonmota.cursomc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	@Transactional
	Page<Pedido> findByCliente (Cliente cliente, PageRequest page);
}
