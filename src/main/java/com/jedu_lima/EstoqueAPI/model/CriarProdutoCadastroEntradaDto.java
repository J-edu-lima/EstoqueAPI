package com.jedu_lima.EstoqueAPI.model;

public record CriarProdutoCadastroEntradaDto(Long codigoDeBarras, String nome, Double valorCompra,
		Integer quantidadeTotal, Integer porcentagemSobreVenda) {

}
