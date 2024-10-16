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

import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;
import com.jedu_lima.EstoqueAPI.model.CriarProdutoSaidaDto;
import com.jedu_lima.EstoqueAPI.service.SaidaService;

@RestController
@RequestMapping(value = "/v1/saida")
public class ProdutoSaidaController {

	private SaidaService saidaService;

	@Autowired
	public ProdutoSaidaController(SaidaService saidaService) {
		this.saidaService = saidaService;
	}

	@PostMapping("/{id}")
	public ResponseEntity<Void> subtrairQuantidade(@RequestBody CriarProdutoSaidaDto criarProdutoSaidaDto,
			@PathVariable("id") Long id) {
		saidaService.subtrairQuantidade(id, criarProdutoSaidaDto);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
		saidaService.excluir(id);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CriarProdutoSaidaDto> buscar(@PathVariable("id") Long id) {
		ProdutoSaida produtoSaida = saidaService.buscar(id);

		return ResponseEntity.ok().body(new CriarProdutoSaidaDto(produtoSaida));
	}

	@GetMapping
	public ResponseEntity<List<CriarProdutoSaidaDto>> buscarTodos() {
		List<ProdutoSaida> produtos = saidaService.buscarTodos();
		List<CriarProdutoSaidaDto> produtoSaidaDto = produtos.stream().map(x -> new CriarProdutoSaidaDto(x))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(produtoSaidaDto);
	}
}
