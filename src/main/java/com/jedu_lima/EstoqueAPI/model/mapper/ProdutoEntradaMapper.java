package com.jedu_lima.EstoqueAPI.model.mapper;

import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoEntradaDto;

public class ProdutoEntradaMapper {

	public ProdutoEntradaMapper(ProdutoEntrada produto) {
	}

	public static ProdutoEntrada paraEntidade(ProdutoCadastro produto, CriarProdutoEntradaDto produtoEntradaDto) {

		return new ProdutoEntrada(produto, produtoEntradaDto.quantidadeEntrada(), LocalDate.now());
	}
}
