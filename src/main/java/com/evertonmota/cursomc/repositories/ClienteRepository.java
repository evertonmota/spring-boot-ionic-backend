package com.evertonmota.cursomc.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evertonmota.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	// É uma transação READONLY. Uma consulta mais rapida e diminui o lock de transações do banco de dados.
	@Transactional
	Cliente findByEmail(String email);
}
