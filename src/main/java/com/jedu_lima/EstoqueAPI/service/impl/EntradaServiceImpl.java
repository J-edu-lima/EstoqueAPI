package com.jedu_lima.EstoqueAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoEntradaDto;
import com.jedu_lima.EstoqueAPI.model.mapper.ProdutoEntradaMapper;
import com.jedu_lima.EstoqueAPI.repository.ProdutoEntradaRepository;
import com.jedu_lima.EstoqueAPI.service.CadastroService;
import com.jedu_lima.EstoqueAPI.service.CalculoService;
import com.jedu_lima.EstoqueAPI.service.EntradaService;

@Service
public class EntradaServiceImpl implements EntradaService {

	private ProdutoEntradaRepository repository;

	@Autowired
	public EntradaServiceImpl(ProdutoEntradaRepository repository) {
		this.repository = repository;
	}

	@Autowired
	private CalculoService calculoService;

	@Autowired
	private CadastroService cadastroService;

	@Override
	public void adicionarQuantidade(Long id, CriarProdutoEntradaDto produtoEntradaDto) {
		ProdutoCadastro produtoCadastro = cadastroService.buscar(id);
		CriarProdutoCadastroEntradaDto produtoDto = criarProdutoCadastroDTO(produtoCadastro, calculoService
				.quantidadeSoma(produtoEntradaDto.quantidadeEntrada(), produtoCadastro.getQuantidadeTotal()));
		cadastroService.atualizar(produtoDto, id);
		ProdutoEntrada produtoEntrada = ProdutoEntradaMapper.paraEntidade(produtoCadastro, produtoEntradaDto);

		repository.save(produtoEntrada);

	}

	@Override
	public void excluir(Long id) {
		ProdutoEntrada produtoEntrada = buscar(id);
		ProdutoCadastro produtoCadastro = produtoEntrada.getProduto();
		CriarProdutoCadastroEntradaDto produtoDto = criarProdutoCadastroDTO(produtoCadastro, calculoService
				.quantidadeSubtracao(produtoEntrada.getQuantidadeEntrada(), produtoCadastro.getQuantidadeTotal()));
		cadastroService.atualizar(produtoDto, id);

		repository.deleteById(id);
	}

	@Override
	public ProdutoEntrada buscar(Long id) {
		Optional<ProdutoEntrada> produto = repository.findById(id);

		return produto.orElseThrow();
	}

	@Override
	public List<ProdutoEntrada> buscarTodos() {

		return repository.findAll();
	}

	private CriarProdutoCadastroEntradaDto criarProdutoCadastroDTO(ProdutoCadastro produtoCadastro, Integer soma) {
		CriarProdutoCadastroEntradaDto produtoDto = new CriarProdutoCadastroEntradaDto(produtoCadastro.getId(),
				produtoCadastro.getCodigoDeBarras(), produtoCadastro.getNome(), produtoCadastro.getValorCompra(), soma,
				produtoCadastro.getPorcentagemSobreVenda(), produtoCadastro.getValorVenda());
		return produtoDto;
	}

}
