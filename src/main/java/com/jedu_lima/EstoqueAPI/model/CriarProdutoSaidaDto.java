package com.jedu_lima.EstoqueAPI.model;

import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public record CriarProdutoSaidaDto(ProdutoCadastro produto, Integer quantidadeSaida, Double totalVenda,
		LocalDate dataSaida) {

}
