package com.evertonmota.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.domain.enums.TipoCliente;
import com.evertonmota.cursomc.dto.ClienteNewDTO;
import com.evertonmota.cursomc.repositories.ClienteRepository;
import com.evertonmota.cursomc.resources.exceptions.FieldMessage;
import com.evertonmota.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator< ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsert clienteInsert) {

	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDTO, ConstraintValidatorContext context) {

		List<FieldMessage> messages = new ArrayList<>();
		
		// Testo se o meu OBJDTO.TIPO é PessoaFisica e  se o CPF nao for válido crio mensagem de erro.
		if(objDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDTO.getCpfCnpj())) {
			messages.add(new FieldMessage ("cpfCnpj","CPF INVÁLIDO."));
		}
		
		if(objDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDTO.getCpfCnpj())) {
			messages.add(new FieldMessage ("cpfCnpj","CNPJ INVÁLIDO."));
		}
		
		Cliente cliente = repository.findByEmail(objDTO.getEmail());
		
		if(cliente != null) {
			messages.add(new FieldMessage("email","Email já existente."));
		}
		
		
		for(FieldMessage m : messages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(m.getFieldName()).addPropertyNode(m.getFieldName()).addConstraintViolation();
		}
		return messages.isEmpty();
	}

	
}
