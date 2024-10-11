package com.jedu_lima.EstoqueAPI.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produto")
public class ProdutoCadastro {

	@Id
	private Long codigoDeBarras;

	private String nome;

	private Double valorCompra;

	private Integer quantidadeTotal;

	private Integer porcentagemSobreVenda;

	private Double valorVenda;

	public ProdutoCadastro() {
	}

	public ProdutoCadastro(Long codigoDeBarras, String nome, Double valorCompra, Integer quantidadeTotal,
			Integer porcentagemSobreVenda) {
		this.codigoDeBarras = codigoDeBarras;
		this.nome = nome;
		this.valorCompra = valorCompra;
		this.quantidadeTotal = quantidadeTotal;
		this.porcentagemSobreVenda = porcentagemSobreVenda;
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

	public Double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(Double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public Integer getQuantidadeTotal() {
		return quantidadeTotal;
	}

	public void setQuantidadeTotal(Integer quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}

	public Integer getPorcentagemSobreVenda() {
		return porcentagemSobreVenda;
	}

	public void setPorcentagemSobreVenda(Integer porcentagemSobreVenda) {
		this.porcentagemSobreVenda = porcentagemSobreVenda;
	}

	public Double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(Double valorVenda) {
		this.valorVenda = valorVenda;
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
