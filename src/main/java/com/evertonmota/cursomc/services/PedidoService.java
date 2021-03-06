package com.evertonmota.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.ItemPedido;
import com.evertonmota.cursomc.domain.PagamentoComBoleto;
import com.evertonmota.cursomc.domain.Pedido;
import com.evertonmota.cursomc.domain.enums.EstadoPagamento;
import com.evertonmota.cursomc.repositories.ItemPedidoRepository;
import com.evertonmota.cursomc.repositories.PagamentoRepository;
import com.evertonmota.cursomc.repositories.PedidoRepository;
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
	
	
	public Pedido find( Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID :" +id + " Tipo : " + Pedido.class.getName())  );
	}
	
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
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
			pedidos.setPreco(produtoService.find(pedidos.getProduto().getId()).getPreco());
			pedidos.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;

	}

}
