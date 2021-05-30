package com.evertonmota.cursomc.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.evertonmota.cursomc.services.exceptions.DataIntegrityException;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

// 
@ControllerAdvice 
public class ResourceExceptionHandler {

	// Padrão do Controler Advice.  Implementando uma Classe Auxiliar que vai interceptar as Exceções.
	// O Método vai receber a Exceção que estorou e as informações da requisição.
	
	@ExceptionHandler(ObjectNotFoundException.class) // Para indicar que é um tratador de exceções deste tipo de exceção.
	public ResponseEntity<StandardError> objectNotFound (ObjectNotFoundException e, HttpServletRequest request) {
		
		// Objeto nao encontrado, a mensagem da exceççao, e o horário local do sistema.
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
	
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegraty (DataIntegrityException e, HttpServletRequest request) {
		
		StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
	
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation (MethodArgumentNotValidException e, HttpServletRequest request) {
		
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro de validação", System.currentTimeMillis()); 
							//  acesso todos erros de campo, que aconteceream nesta exceção.
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			
			err.addErrors(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
