package com.jedu_lima.EstoqueAPI.model;

import java.math.BigDecimal;

public record CriarProdutoCadastroEntradaDto(Long codigoDeBarras, String nome, BigDecimal valorCompra,
		Integer quantidadeTotal, Double porcentagemSobreVenda) {

}
