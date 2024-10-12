package com.jedu_lima.EstoqueAPI.service;

import java.math.BigDecimal;

public interface CalculoService {

	BigDecimal calcularValorVenda(BigDecimal valorCompra, Double porcentagem);
	Integer quantidadeSoma(Integer quantidadeEntrada, Integer quantidadeTotal);
	Integer quantidadeSubtracao(Integer quantidadeEntrada, Integer quantidadeTotal);
	BigDecimal calcularValorTotalVenda(BigDecimal valorVednda, Integer quantidadeSaida);
}
