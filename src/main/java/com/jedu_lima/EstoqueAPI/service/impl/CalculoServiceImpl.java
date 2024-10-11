package com.jedu_lima.EstoqueAPI.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.jedu_lima.EstoqueAPI.service.CalculoService;

@Service
public class CalculoServiceImpl implements CalculoService {

	@Override
	public BigDecimal calcularValorVenda(BigDecimal valorCompra, Double porcentagem) {
		Double porcentagemReal = 100.0 - porcentagem;
		Double valorPorcentagem = porcentagemReal / 100.0;
		Double valorVenda = valorCompra.doubleValue() / valorPorcentagem;

		BigDecimal valorConvertido = new BigDecimal(valorVenda);

		return valorConvertido;
	}

}
