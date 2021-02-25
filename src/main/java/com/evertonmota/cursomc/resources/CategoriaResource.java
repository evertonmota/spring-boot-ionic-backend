package com.evertonmota.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evertonmota.cursomc.domain.Categoria;
import com.evertonmota.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	
	@RequestMapping( value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> find (@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return  ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	// @RequestBody  Faz com que o JSON seja convertido por objeto java automaticamente.
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){
		
		// A operação save do repository, retorna um object.
		obj = service.insert(obj); 

		// recuperar um novo id e fornecer como argumento da URI.
		// fromCurrentRequest() = http://127.0.0.1:8080/categorias 
		// .path("/{id}"); /3
		
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(url).build();
		 
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody	Categoria obj , @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
}
