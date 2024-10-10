package com.jedu_lima.EstoqueAPI.model.mapper;

import java.time.LocalDate;

import com.jedu_lima.EstoqueAPI.entity.Produto;
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.model.ProdutoEntradaDto;

public class ProdutoEntradaMapper {

	private ProdutoEntradaMapper(Produto produto) {
	}

	public static Produto paraEntidade(ProdutoEntradaDto dto, Produto produto) {

		Long id = dto.id();
		String nome = dto.nome();
		Double preco = dto.preco();
		Integer quantidade = dto.quantidade();
		Long codigoDeBarras = dto.codigoDeBarras();

		return new ProdutoEntrada(id, codigoDeBarras, nome, preco, quantidade, LocalDate.now());
	}
}
