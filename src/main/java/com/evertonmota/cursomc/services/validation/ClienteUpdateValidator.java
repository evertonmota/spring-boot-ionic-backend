package com.evertonmota.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.dto.ClienteDTO;
import com.evertonmota.cursomc.repositories.ClienteRepository;
import com.evertonmota.cursomc.resources.exceptions.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator< ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteUpdate clienteUpdate) {

	}
	
	@Override
	public boolean isValid(ClienteDTO objDTO, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String , String> map = (Map<String , String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		
		
		List<FieldMessage> messages = new ArrayList<>();
		
		Cliente cliente = repository.findByEmail(objDTO.getEmail());
		if(cliente != null && !cliente.getId().equals(uriId)) {
			messages.add(new FieldMessage("email","Email já existente."));
		}
		
		
		for(FieldMessage m : messages) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(m.getFieldName()).addPropertyNode(m.getFieldName()).addConstraintViolation();
		}
		return messages.isEmpty();
	}

	
}
