package com.jedu_lima.EstoqueAPI.model;

import java.math.BigDecimal;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public record CriarProdutoCadastroEntradaDto(Long id, Long codigoDeBarras, String nome, BigDecimal valorCompra,
		Integer quantidadeTotal, Double porcentagemSobreVenda) {

	public CriarProdutoCadastroEntradaDto(ProdutoCadastro x) {
		this(x.getId(), x.getCodigoDeBarras(), x.getNome(), x.getValorCompra(), x.getQuantidadeTotal(),
				x.getPorcentagemSobreVenda());
	}

}
