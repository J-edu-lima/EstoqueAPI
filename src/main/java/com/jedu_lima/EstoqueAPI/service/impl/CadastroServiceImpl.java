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

@Service
public class CadastroServiceImpl implements CadastroService {

	@Autowired
	private CalculoService calculoService;

	private ProdutoCadastroRepository repository;

	@Autowired
	public CadastroServiceImpl(ProdutoCadastroRepository repository) {
		this.repository = repository;
	}

	@Override
	public void cadastro(CriarProdutoCadastroEntradaDto produto) {
		ProdutoCadastro produtoCadastro = ProdutoCadastroMapper.paraEntidade(produto);
		BigDecimal valorVenda = calculoService.calcularValorVenda(produtoCadastro.getValorCompra(),
				produtoCadastro.getPorcentagemSobreVenda());
		produtoCadastro.setValorVenda(valorVenda);

		repository.save(produtoCadastro);
	}

	@Override
	public void exlcuir(Long codigo) {

		repository.deleteByCodigoDeBarras(codigo);
	}

	@Override
	public ProdutoCadastro buscar(Long codigo) {
		Optional<ProdutoCadastro> produto = repository.findByCodigoDeBarras(codigo);

		return produto.orElseThrow();
	}

	@Override
	public List<ProdutoCadastro> buscarTodos() {

		return repository.findAll();
	}

	@Override
	public ProdutoCadastro atualizar(CriarProdutoCadastroEntradaDto novoProdutoDto, ProdutoCadastro produtoAtual) {
		ProdutoCadastro novoProduto = ProdutoCadastroMapper.paraEntidade(novoProdutoDto);
		produtoAtual.setNome(novoProduto.getNome());
		produtoAtual.setValorCompra(novoProduto.getValorCompra());
		produtoAtual.setPorcentagemSobreVenda(novoProduto.getPorcentagemSobreVenda());

		return repository.save(produtoAtual);
	}

	@Override
	public ProdutoCadastro atualizarQuantidade(ProdutoCadastro produto, Integer quantidade) {
		produto.setQuantidadeTotal(quantidade);

		return repository.save(produto);
	}

}
