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

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.dto.ClienteDTO;
import com.evertonmota.cursomc.dto.ClienteNewDTO;
import com.evertonmota.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@RequestMapping( value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find (@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return  ResponseEntity.ok().body(obj);
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	// @RequestBody  Faz com que o JSON seja convertido por objeto java automaticamente.
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO){
		
		Cliente obj = service.fromDTO(objDTO);
		// A operação save do repository, retorna um object.
		obj = service.insert(obj); 

		// recuperar um novo id e fornecer como argumento da URI.
		// fromCurrentRequest() = http://127.0.0.1:8080/Clientes 
		// .path("/{id}"); /3
		URI url = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(url).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody	ClienteDTO objDTO , @PathVariable Integer id){
		
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	
	// DELETE
	@RequestMapping( value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Cliente> delete (@PathVariable Integer id) {
 		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Listar Todas as Clientes.
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity <List<ClienteDTO>> findAll () {
		
		List <Cliente> listaClientes = service.findAll();
		
		// Cada elemento obj da lista, uso operador , funcao anonima, convertendo uma lista de um tipo para outra tipo usando stream().
		List<ClienteDTO> listaClientesDTO = listaClientes.stream().map(obj -> new ClienteDTO (obj)).collect(Collectors.toList());
		
		return  ResponseEntity.ok().body(listaClientesDTO);
	}
	
	//Listar Todas as Clientes.
		@RequestMapping(value="/page",method=RequestMethod.GET)
		public ResponseEntity <Page<ClienteDTO>> findPage(
				@RequestParam(value="page",defaultValue = "0") Integer page, 
				@RequestParam(value="linesPerPage",defaultValue = "24")Integer linesPerPage, 
				@RequestParam(value="orderBy",defaultValue = "nome")String orderBy, 
				@RequestParam(value="direction",defaultValue = "ASC")String direction) {
			
			Page <Cliente> listaClientes = service.findPage(page, linesPerPage, orderBy, direction);
			
			// Cada elemento obj da lista, uso operador , funcao anonima
			//convertendo uma lista de um tipo para outra tipo usando stream().
			Page<ClienteDTO> listaClientesDTO = listaClientes.map(obj -> new ClienteDTO (obj));
			
			return  ResponseEntity.ok().body(listaClientesDTO);
		}
}
