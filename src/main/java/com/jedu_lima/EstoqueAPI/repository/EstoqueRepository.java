package com.jedu_lima.EstoqueAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jedu_lima.EstoqueAPI.entity.Produto;

public interface EstoqueRepository extends JpaRepository<Produto, Long> {

}
