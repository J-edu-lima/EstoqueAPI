package com.jedu_lima.EstoqueAPI.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_saida")
public class ProdutoSaida {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private ProdutoCadastro produto;

	private Integer quantidadeSaida;

	@Column(precision = 10, scale = 2)
	private BigDecimal totalVenda;

	private LocalDate dataSaida;

	public ProdutoSaida() {
	}

	public ProdutoSaida(ProdutoCadastro produto, Integer quantidadeSaida, BigDecimal totalVenda, LocalDate dataSaida) {
		this.produto = produto;
		this.quantidadeSaida = quantidadeSaida;
		this.totalVenda = totalVenda;
		this.dataSaida = dataSaida;
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

	public Integer getQuantidadeSaida() {
		return quantidadeSaida;
	}

	public void setQuantidadeSaida(Integer quantidadeSaida) {
		this.quantidadeSaida = quantidadeSaida;
	}

	public BigDecimal getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(BigDecimal totalVenda) {
		this.totalVenda = totalVenda;
	}

	public LocalDate getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDate dataSaida) {
		this.dataSaida = dataSaida;
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
		ProdutoSaida other = (ProdutoSaida) obj;
		return Objects.equals(id, other.id);
	}

}
