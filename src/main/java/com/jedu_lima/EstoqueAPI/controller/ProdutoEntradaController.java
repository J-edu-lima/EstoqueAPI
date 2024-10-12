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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoEntradaDto;
import com.jedu_lima.EstoqueAPI.service.EntradaService;

@RestController
@RequestMapping(value = "/v1/produto_entrada")
public class ProdutoEntradaController {

	private EntradaService entradaService;

	@Autowired
	public ProdutoEntradaController(EntradaService entradaService) {
		this.entradaService = entradaService;
	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> adicionarQuantidade(@RequestBody CriarProdutoEntradaDto criarProdutoEntradaDto,
			@PathVariable("id") Long id) {
		entradaService.adicionarQuantidade(id, criarProdutoEntradaDto);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		entradaService.excluir(id);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CriarProdutoEntradaDto> buscar(@PathVariable("id") Long id) {
		ProdutoEntrada produtoEntrada = entradaService.buscar(id);

		return ResponseEntity.ok().body(new CriarProdutoEntradaDto(produtoEntrada));
	}

	@GetMapping
	public ResponseEntity<List<CriarProdutoEntradaDto>> buscarTodos() {
		List<ProdutoEntrada> produtos = entradaService.buscarTodos();
		List<CriarProdutoEntradaDto> produtoEntradaDto = produtos.stream().map(x -> new CriarProdutoEntradaDto(x))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(produtoEntradaDto);
	}
}
