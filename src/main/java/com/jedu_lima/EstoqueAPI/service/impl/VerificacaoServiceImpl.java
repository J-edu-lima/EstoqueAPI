package com.jedu_lima.EstoqueAPI.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public class VerificacaoServiceImpl {

	@Autowired
	CadastroServiceImpl service;

	public boolean verificarNome(String nome, Long codigo) {

		List<ProdutoCadastro> produtos = service.buscarTodos();
		for (ProdutoCadastro produto : produtos) {
			if (nome.equals(produto.getNome()) || codigo.equals(produto.getCodigoDeBarras())) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarNumeroNegativo(BigDecimal numeroValor, Double numeroPorcentagem, Integer numeroQuantidade) {
		BigDecimal novaPorcentagem = new BigDecimal(numeroPorcentagem);
		BigDecimal novaQuantidade = new BigDecimal(numeroQuantidade);

		if (numeroValor.signum() == -1 || novaPorcentagem.signum() == -1 || novaQuantidade.signum() == -1) {
			return true;
		}
		return false;
	}

	public boolean verificarPorcentagem(Double numero) {
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
