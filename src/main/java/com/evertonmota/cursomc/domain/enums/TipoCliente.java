package com.evertonmota.cursomc.domain.enums;

public enum TipoCliente {

	PESSOAFISICA(1,"Pessoa Fisica"),
	PESSOAJURIDICA(2,"Pessoa Juridica");

	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		 this.cod = cod;
		 this.descricao = descricao;
	}

	// Uma vez que você instância um Enumerado, voce nao muda o nome dele. Então somente o getDescription
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	//Esta operação devera ser possivel de ser executada, mesmo sem instanciar obejtos.
	public static TipoCliente toEnum(Integer id) {
		
		if( id == null) {
			return null;
		}
		
		for(TipoCliente tipoCliente : TipoCliente.values()) {
			if(id.equals(tipoCliente.getCod())) {
				return tipoCliente;
			}
		}
		throw new IllegalArgumentException("Id inválido." + id);
	}
	
}
