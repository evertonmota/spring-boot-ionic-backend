package com.evertonmota.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pedido implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date instante;

	// Isso é necessário porque se nao da um erro de Entidade Transiente. 
	// Quando for salvar um pedido e o pagamento dele.
	// Mapeamento Bidirecional de 1 p 1.
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy = "pedido")
	private Pagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	// A classe pedido vai ter um conjunto de ItemPedido
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
		
	}
	
	public Pedido(Integer id, Date instante,Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public double getValorTotal() {
		double soma = 0.00;
		for(ItemPedido ip : itens) {
			soma+= ip.getSubTotal();
		}
		return soma;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {

		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM:ss");
		
		StringBuilder builder = new StringBuilder();
		builder.append("Nº do Pedido : ");
		builder.append(getId());
		builder.append(", Data : ");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente : ");
		builder.append(getCliente().getNome());
		builder.append("\n");
		builder.append(", Situação do Pagamento : ");
		builder.append(getPagamento().getTipoPagamento().getDescricao());
		builder.append("\nDetalhes :\n");
		
		for(ItemPedido ip : getItens()) {
			builder.append(ip.toString());
		}
		
		builder.append("Valor Total :");
		builder.append(nf.format(getValorTotal()));
		return builder.toString();
	}

	
}
