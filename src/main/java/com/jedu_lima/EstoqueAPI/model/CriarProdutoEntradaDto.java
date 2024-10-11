package com.jedu_lima.EstoqueAPI.model;

import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public record CriarProdutoEntradaDto(ProdutoCadastro produto, Integer quantidadeEntrada, LocalDate dataEntrada) {

}
