package com.jedu_lima.EstoqueAPI.entity;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_entrada")
public class ProdutoEntrada {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private ProdutoCadastro produto;

	private Integer quantidadeEntrada;

	private LocalDate dataEntrada;

	public ProdutoEntrada() {
	}

	public ProdutoEntrada(ProdutoCadastro produto, Integer quantidadeEntrada, LocalDate dataEntrada) {
		this.produto = produto;
		this.quantidadeEntrada = quantidadeEntrada;
		this.dataEntrada = dataEntrada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProdutoCadastro getProduto() {
		return produto;
	}

	public void setProduto(ProdutoCadastro produto) {
		this.produto = produto;
	}

	public Integer getQuantidadeEntrada() {
		return quantidadeEntrada;
	}

	public void setQuantidadeEntrada(Integer quantidadeEntrada) {
		this.quantidadeEntrada = quantidadeEntrada;
	}

	public LocalDate getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDate dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoEntrada other = (ProdutoEntrada) obj;
		return Objects.equals(id, other.id);
	}

}
