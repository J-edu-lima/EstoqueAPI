package com.jedu_lima.EstoqueAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
