package com.evertonmota.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.Cliente;
import com.evertonmota.cursomc.domain.ItemPedido;
import com.evertonmota.cursomc.domain.PagamentoComBoleto;
import com.evertonmota.cursomc.domain.Pedido;
import com.evertonmota.cursomc.domain.enums.EstadoPagamento;
import com.evertonmota.cursomc.repositories.ItemPedidoRepository;
import com.evertonmota.cursomc.repositories.PagamentoRepository;
import com.evertonmota.cursomc.repositories.PedidoRepository;
import com.evertonmota.cursomc.security.UserSS;
import com.evertonmota.cursomc.services.exceptions.AuthorizationException;
import com.evertonmota.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	//Instânciar o repositório 
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	BoletoService boletoService ;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find( Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Pedido.class.getName())  );
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setTipoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			 PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			 boletoService.preencherPagamentoComBoleto( pagto , obj.getInstante());
		}
		obj = repo.save(obj); 
		pagamentoRepository.save(obj.getPagamento());
		
		for(ItemPedido pedidos : obj.getItens()) {
			pedidos.setDesconto(0.0);
			pedidos.setProduto(produtoService.find(pedidos.getProduto().getId()));;
			pedidos.setPreco(pedidos.getProduto().getPreco());
			pedidos.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		//emailService.sendOrderConfirmationEmail(obj);
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
		
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		UserSS user = UserService.authenticated();
		
		if(user == null) {
			throw new AuthorizationException("Acesso negado.");
		}
		
		PageRequest pageRequest = PageRequest.of(page,linesPerPage,Direction.valueOf(direction), orderBy );
		
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}


}
