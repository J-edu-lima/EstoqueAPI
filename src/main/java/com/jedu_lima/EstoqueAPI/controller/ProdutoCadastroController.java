package com.jedu_lima.EstoqueAPI.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoCadastroEntradaDto;
import com.jedu_lima.EstoqueAPI.service.CadastroService;

@RestController
@RequestMapping(value = "/v1/produto_cadastro")
public class ProdutoCadastroController {

	private CadastroService cadastroService;

	@Autowired
	public ProdutoCadastroController(CadastroService cadastroService) {
		this.cadastroService = cadastroService;
	}

	@PostMapping
	public ResponseEntity<Void> cadastrar(@RequestBody CriarProdutoCadastroEntradaDto criarProdutoTarefaEntradaDto) {
		cadastroService.cadastro(criarProdutoTarefaEntradaDto);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long codigoDeBarras) {
		cadastroService.excluir(codigoDeBarras);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CriarProdutoCadastroEntradaDto> buscar(@PathVariable("id") Long codigoDeBarras) {
		ProdutoCadastro produto = cadastroService.buscar(codigoDeBarras);

		return ResponseEntity.ok().body(new CriarProdutoCadastroEntradaDto(produto));
	}

	@GetMapping
	public ResponseEntity<List<CriarProdutoCadastroEntradaDto>> buscarTodos() {
		List<ProdutoCadastro> produtos = cadastroService.buscarTodos();
		List<CriarProdutoCadastroEntradaDto> produtosEntradaDto = produtos.stream()
				.map(x -> new CriarProdutoCadastroEntradaDto(x)).collect(Collectors.toList());

		return ResponseEntity.ok().body(produtosEntradaDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> atualizar(@RequestBody CriarProdutoCadastroEntradaDto novaTarefa,
			@PathVariable Long id) {
		cadastroService.atualizar(novaTarefa, id);

		return ResponseEntity.accepted().build();
	}
}
