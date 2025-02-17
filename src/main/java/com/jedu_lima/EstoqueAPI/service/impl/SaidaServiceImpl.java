package com.jedu_lima.EstoqueAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoSaidaDto;
import com.jedu_lima.EstoqueAPI.model.mapper.ProdutoSaidaMapper;
import com.jedu_lima.EstoqueAPI.repository.ProdutoSaidaRepository;
import com.jedu_lima.EstoqueAPI.service.CadastroService;
import com.jedu_lima.EstoqueAPI.service.CalculoService;
import com.jedu_lima.EstoqueAPI.service.SaidaService;

@Service
public class SaidaServiceImpl implements SaidaService {

	private ProdutoSaidaRepository repository;

	@Autowired
	public SaidaServiceImpl(ProdutoSaidaRepository repository) {
		this.repository = repository;
	}

	@Autowired
	private CalculoService calculoService;

	@Autowired
	private CadastroService cadastroService;

	@Override
	public void subtrairQuantidade(Long id, CriarProdutoSaidaDto produtoSaidaDto) {
		ProdutoCadastro produtoCadastro = cadastroService.buscar(id);
		CriarProdutoCadastroEntradaDto produtoDto = criarProdutoCadastroDTO(produtoCadastro, calculoService
				.quantidadeSubtracao(produtoSaidaDto.quantidadeSaida(), produtoCadastro.getQuantidadeTotal()));
		cadastroService.atualizar(produtoDto, id);
		ProdutoSaida produtoSaida = ProdutoSaidaMapper.paraEntidade(produtoCadastro, produtoSaidaDto);
		produtoSaida.setTotalVenda(calculoService.calcularValorTotalVenda(produtoCadastro.getValorSugerido(),
				produtoSaida.getQuantidadeSaida()));

		repository.save(produtoSaida);

	}

	@Override
	public void excluir(Long id) {
		ProdutoSaida produtoSaida = buscar(id);
		ProdutoCadastro produtoCadastro = produtoSaida.getProduto();
		CriarProdutoCadastroEntradaDto produtoDto = criarProdutoCadastroDTO(produtoCadastro,
				calculoService.quantidadeSoma(produtoSaida.getQuantidadeSaida(), produtoCadastro.getQuantidadeTotal()));
		cadastroService.atualizar(produtoDto, produtoDto.id());

		repository.deleteById(id);
	}

	@Override
	public ProdutoSaida buscar(Long id) {
		Optional<ProdutoSaida> produto = repository.findById(id);

		return produto.orElseThrow();
	}

	@Override
	public List<ProdutoSaida> buscarTodos() {

		return repository.findAll();
	}

	private CriarProdutoCadastroEntradaDto criarProdutoCadastroDTO(ProdutoCadastro produtoCadastro, Integer soma) {
		CriarProdutoCadastroEntradaDto produtoDto = new CriarProdutoCadastroEntradaDto(produtoCadastro.getId(),
				produtoCadastro.getCodigoDeBarras(), produtoCadastro.getNome(), produtoCadastro.getValorCompra(), soma,
				produtoCadastro.getPorcentagemSobreVenda(), produtoCadastro.getValorSugerido());
		return produtoDto;
	}
}
