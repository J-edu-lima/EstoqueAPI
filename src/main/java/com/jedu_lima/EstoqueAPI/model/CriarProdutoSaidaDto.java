package com.jedu_lima.EstoqueAPI.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public record CriarProdutoSaidaDto(ProdutoCadastro produto, Integer quantidadeSaida, BigDecimal totalVenda,
		LocalDate dataSaida) {

}
