package com.jedu_lima.EstoqueAPI.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;

@Entity
public class ProdutoEntrada extends Produto{
	
	private LocalDate dataEntrada;
	
	public ProdutoEntrada() {
		
	}

	public ProdutoEntrada(Long id, String nome, Double preco, Integer quantidade, LocalDate dataEntrada) {
		super(id, nome, preco, quantidade);
		this.setDataEntrada(dataEntrada);
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}
	
}
