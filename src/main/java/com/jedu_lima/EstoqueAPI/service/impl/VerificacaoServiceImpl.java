package com.jedu_lima.EstoqueAPI.service.impl;

import java.math.BigDecimal;

public class VerificacaoServiceImpl {

	public boolean verificarNome(String nome1, String nome2) {
		return (nome1.contentEquals(nome2));
	}

	public boolean verificarCodigoBarras(Long codigo1, Long codigo2) {
		return codigo1.equals(codigo2);
	}

	public boolean verificarNumeroNegativo(BigDecimal numero) {
		return numero.signum() == -1;
	}

	public boolean verificarNumeroNegativo(Double numero) {
		BigDecimal novoNumero = new BigDecimal(numero);
		return novoNumero.signum() == -1;
	}

	public boolean verificarNumeroNegativo(Integer numero) {
		BigDecimal novoNumero = new BigDecimal(numero);
		return novoNumero.signum() == -1;
	}

	public boolean verfiicarPorcentagem(Double numero) {
		BigDecimal novoNumero = new BigDecimal(numero);
		return novoNumero.signum() >= 100;
	}

	public boolean verificarQuantidadeParaVenda(Integer quantidade1, Integer quantidade2) {
		Integer resultado = Integer.compare(quantidade1, quantidade2);
		if (resultado == 1) {
			return true;
		}
		return false;
	}
}
