package com.jedu_lima.EstoqueAPI.service;

import java.util.List;

import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoEntradaDto;

public interface EntradaService {
	
	void adicionarQuantidade(Long id, CriarProdutoEntradaDto produtoEntrada);
	void excluir(Long id);
	ProdutoEntrada buscar(Long id);
	List<ProdutoEntrada> buscarTodos();	

}
