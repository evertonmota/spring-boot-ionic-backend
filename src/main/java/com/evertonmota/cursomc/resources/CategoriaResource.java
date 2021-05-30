package com.evertonmota.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evertonmota.cursomc.domain.Categoria;
import com.evertonmota.cursomc.dto.CategoriaDTO;
import com.evertonmota.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;


	// ResponseEntity -> É um objeto complexo que vai ter Http como Resposta e varias informações do protocolo HTTTP.
	// uma forma de dizer que ocorreu tudo bem. É colocar ResponseEntity.ok e como o corpo o objeto obj que busquei CATEGORIA.

	@RequestMapping( value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> find (@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return  ResponseEntity.ok().body(obj);
	}

	
	@RequestMapping(method=RequestMethod.POST)
	// @RequestBody  Faz com que o JSON seja convertido por objeto java automaticamente.
	
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){

		Categoria obj = service.fromDTO(objDTO);

		// A operação save do repository, retorna um object.
		obj = service.insert(obj); 

		// recuperar um novo id e fornecer como argumento da URI.
		// fromCurrentRequest() = http://127.0.0.1:8080/categorias 
		// .path("/{id}"); /3
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(url).build(); // gera o 201 created
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody	CategoriaDTO objDTO , @PathVariable Integer id){

		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}


	// DELETE
	@RequestMapping( value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Categoria> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	//Listar Todas as Categorias.
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity <List<CategoriaDTO>> findAll () {

		List <Categoria> listaCategorias = service.findAll();

		// Cada elemento obj da lista, uso operador , funcao anonima
		//convertendo uma lista de um tipo para outra tipo usando stream().
		List<CategoriaDTO> listaCategoriasDTO = listaCategorias.stream().map(obj -> new CategoriaDTO (obj)).collect(Collectors.toList());

		return  ResponseEntity.ok().body(listaCategoriasDTO);
	}

	//Listar Todas as Categorias.
	@RequestMapping(value="/page",method=RequestMethod.GET)
	public ResponseEntity <Page<CategoriaDTO>> findPage(
			@RequestParam(value="page",defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage",defaultValue = "24")Integer linesPerPage, 
			@RequestParam(value="orderBy",defaultValue = "nome")String orderBy, 
			@RequestParam(value="direction",defaultValue = "ASC")String direction) {

		Page <Categoria> listaCategorias = service.findPage(page, linesPerPage, orderBy, direction);
		// Cada elemento obj da lista, uso operador , funcao anonima convertendo uma lista de um tipo para outra tipo usando stream().
		Page<CategoriaDTO> listaCategoriasDTO = listaCategorias.map(obj -> new CategoriaDTO (obj));

		return  ResponseEntity.ok().body(listaCategoriasDTO);
	}


}
