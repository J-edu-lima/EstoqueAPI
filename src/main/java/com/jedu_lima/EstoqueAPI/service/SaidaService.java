package com.jedu_lima.EstoqueAPI.service;

import java.util.List;

import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoSaidaDto;

public interface SaidaService {

	void subtrairQuantidade(Long id, CriarProdutoSaidaDto produtoSaidaDto);
	void excluir(Long id);
	ProdutoSaida buscar(Long id);
	List<ProdutoSaida> buscarTodos();

}
