package com.jedu_lima.EstoqueAPI.service;

import java.util.List;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;

public interface CadastroService {

	void cadastro(CriarProdutoCadastroEntradaDto produto);
	void exlcuir(Long codigo);
	ProdutoCadastro buscar(Long codigo);
	List<ProdutoCadastro> buscarTodos();
	ProdutoCadastro atualizar(CriarProdutoCadastroEntradaDto novoProdutoDto, ProdutoCadastro produto);
	ProdutoCadastro atualizarQuantidade(ProdutoCadastro produto, Integer quantidade);
}
