package com.jedu_lima.EstoqueAPI.entity;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class ProdutoCadastro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long codigoDeBarras;

	private String nome;

	private BigDecimal valorCompra;

	private Integer quantidadeTotal;

	private Double porcentagemSobreVenda;

	@Column(precision = 10, scale = 2)
	private BigDecimal valorSugerido;

	public ProdutoCadastro() {
	}

	public ProdutoCadastro(Long id, Long codigoDeBarras, String nome, BigDecimal valorCompra, Integer quantidadeTotal,
			Double porcentagemSobreVenda) {
		this.id = id;
		this.codigoDeBarras = codigoDeBarras;
		this.nome = nome;
		this.valorCompra = valorCompra;
		this.quantidadeTotal = quantidadeTotal;
		this.porcentagemSobreVenda = porcentagemSobreVenda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(Long codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(BigDecimal valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Integer getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(Integer quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public Double getPorcentagemSobreVenda() {
		return porcentagemSobreVenda;
	}

	public void setPorcentagemSobreVenda(Double porcentagemSobreVenda) {
		this.porcentagemSobreVenda = porcentagemSobreVenda;
	}

	public BigDecimal getValorSugerido() {
		return valorSugerido;
	}

	public void setValorSugerido(BigDecimal valorSugerido) {
		this.valorSugerido = valorSugerido;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoDeBarras);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProdutoCadastro other = (ProdutoCadastro) obj;
		return Objects.equals(codigoDeBarras, other.codigoDeBarras);
	}

}
