package com.jedu_lima.EstoqueAPI.model;

import java.time.LocalDate;

public record ProdutoEntradaDto(Long id, Long codigoDeBarras, String nome, Double preco, Integer quantidade, LocalDate dataEntrada) {

}
