package com.jedu_lima.EstoqueAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;

public interface ProdutoSaidaRepository extends JpaRepository<ProdutoSaida, Long> {

}
