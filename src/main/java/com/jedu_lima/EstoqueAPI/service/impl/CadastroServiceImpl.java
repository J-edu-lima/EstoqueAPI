package com.jedu_lima.EstoqueAPI.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;
import com.jedu_lima.EstoqueAPI.model.mapper.ProdutoCadastroMapper;
import com.jedu_lima.EstoqueAPI.repository.ProdutoCadastroRepository;
import com.jedu_lima.EstoqueAPI.service.CadastroService;
import com.jedu_lima.EstoqueAPI.service.CalculoService;

import jakarta.transaction.Transactional;

@Service
public class CadastroServiceImpl implements CadastroService {

	private ProdutoCadastroRepository repository;

	@Autowired
	public CadastroServiceImpl(ProdutoCadastroRepository repository) {
		this.repository = repository;
	}

	@Autowired
	private CalculoService calculoService;

	@Override
	public void cadastro(CriarProdutoCadastroEntradaDto produto) {
		ProdutoCadastro produtoCadastro = ProdutoCadastroMapper.paraEntidade(produto);
		BigDecimal valorVenda = calculoService.calcularValorVenda(produtoCadastro.getValorCompra(),
				produtoCadastro.getPorcentagemSobreVenda());
		produtoCadastro.setValorVenda(valorVenda);

		repository.save(produtoCadastro);
	}

	@Override
	public void excluir(Long id) {

		repository.deleteById(id);
	}

	@Override
	public ProdutoCadastro buscar(Long id) {
		Optional<ProdutoCadastro> produto = repository.findById(id);

		return produto.orElseThrow();
	}

	@Override
	public List<ProdutoCadastro> buscarTodos() {

		return repository.findAll();
	}

	@Transactional
	@Override
	public ProdutoCadastro atualizar(CriarProdutoCadastroEntradaDto novoProdutoDto, Long id) {
		ProdutoCadastro produtoAtual = buscar(id);
		atualizarDados(novoProdutoDto, produtoAtual);

		return repository.save(produtoAtual);
	}

	private void atualizarDados(CriarProdutoCadastroEntradaDto novoProdutoDto, ProdutoCadastro produtoAtual) {
		ProdutoCadastro novoProduto = ProdutoCadastroMapper.paraEntidade(novoProdutoDto);

		if (novoProduto.getCodigoDeBarras() != null) {
			produtoAtual.setCodigoDeBarras(novoProduto.getCodigoDeBarras());
		}
		if (novoProduto.getNome() != null) {
			produtoAtual.setNome(novoProduto.getNome());
		}
		if (novoProduto.getQuantidadeTotal() != null) {
			produtoAtual.setQuantidadeTotal(novoProduto.getQuantidadeTotal());
		}
		if (novoProduto.getPorcentagemSobreVenda() != null) {
			produtoAtual.setPorcentagemSobreVenda(novoProduto.getPorcentagemSobreVenda());
			BigDecimal valorVenda = calculoService.calcularValorVenda(produtoAtual.getValorCompra(),
					novoProduto.getPorcentagemSobreVenda());
			produtoAtual.setValorVenda(valorVenda);
		}
		if (novoProduto.getValorCompra() != null) {
			produtoAtual.setValorCompra(novoProduto.getValorCompra());
			BigDecimal valorVenda = calculoService.calcularValorVenda(novoProduto.getValorCompra(),
					novoProduto.getPorcentagemSobreVenda());
			produtoAtual.setValorVenda(valorVenda);
		}
	}
}
