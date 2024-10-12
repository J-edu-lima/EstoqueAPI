package com.jedu_lima.EstoqueAPI.service;

import java.util.List;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;

public interface CadastroService {

	void cadastro(CriarProdutoCadastroEntradaDto produto);
	void excluir(Long id);
	ProdutoCadastro buscar(Long codigo);
	List<ProdutoCadastro> buscarTodos();
	ProdutoCadastro atualizar(CriarProdutoCadastroEntradaDto novoProdutoDto, Long id);
	
}
