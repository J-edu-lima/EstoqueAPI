package com.jedu_lima.EstoqueAPI.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;

public record CriarProdutoSaidaDto(Long id, ProdutoCadastro produto, Integer quantidadeSaida, BigDecimal totalVenda,
		LocalDate dataSaida) {

	public CriarProdutoSaidaDto(ProdutoSaida x) {
		this(x.getId(), x.getProduto(), x.getQuantidadeSaida(), x.getTotalVenda(), x.getDataSaida());
	}
}
