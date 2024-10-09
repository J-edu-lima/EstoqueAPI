package com.jedu_lima.EstoqueAPI.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class ProdutoSaida extends Produto {

	private LocalDate dataSaida;

	public ProdutoSaida() {

	}

	public ProdutoSaida(Long id, String nome, Double preco, Integer quantidade, LocalDate dataSaida) {
		super(id, nome, preco, quantidade);
		this.setDataSaida(dataSaida);
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
	}

}
