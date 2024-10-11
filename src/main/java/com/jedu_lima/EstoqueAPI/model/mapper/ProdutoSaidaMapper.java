package com.jedu_lima.EstoqueAPI.model.mapper;

import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoSaidaDto;

public class ProdutoSaidaMapper {

	public ProdutoSaidaMapper(ProdutoSaida produto) {
	}

	public static ProdutoSaida paraEntidade(ProdutoCadastro produto, CriarProdutoSaidaDto dtoSaida) {

		return new ProdutoSaida(produto, dtoSaida.quantidadeSaida(), dtoSaida.totalVenda(), LocalDate.now());
	}
}
