package com.jedu_lima.EstoqueAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public interface ProdutoCadastroRepository extends JpaRepository<ProdutoCadastro, Long> {

}
