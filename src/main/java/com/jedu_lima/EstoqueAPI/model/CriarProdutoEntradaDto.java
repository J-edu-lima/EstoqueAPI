package com.jedu_lima.EstoqueAPI.model;

import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;

public record CriarProdutoEntradaDto(Long id, ProdutoCadastro produto, Integer quantidadeEntrada, LocalDate dataEntrada) {

	public CriarProdutoEntradaDto(ProdutoEntrada x) {
		this(x.getId(), x.getProduto(), x.getQuantidadeEntrada(), x.getDataEntrada());
	}

}
