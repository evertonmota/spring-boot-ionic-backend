package com.evertonmota.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.evertonmota.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	
	public void preencherPagamentoComBoleto( PagamentoComBoleto boleto, Date instanteDoPedido) {
		
		// No Mundo real - Chamaria um WEBSERVICE que gera o boleto com a data de pagaemnto.
		Calendar calendar =  Calendar.getInstance();
		calendar.setTime(instanteDoPedido);
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		boleto.setDataVencimento(calendar.getTime()); 
	}
}
