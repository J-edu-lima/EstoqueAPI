package com.jedu_lima.EstoqueAPI.model.mapper;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;

public class ProdutoCadastroMapper {

	public ProdutoCadastroMapper(ProdutoCadastro produto) {
	}

	public static ProdutoCadastro paraEntidade(CriarProdutoCadastroEntradaDto dto) {

		return new ProdutoCadastro(dto.id(), dto.codigoDeBarras(), dto.nome(), dto.valorCompra(), 0,
				dto.porcentagemSobreVenda());
	}
}
