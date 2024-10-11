package com.jedu_lima.EstoqueAPI.service;

import java.math.BigDecimal;

public interface CalculoService {

	BigDecimal calcularValorVenda(BigDecimal valorCompra, Double porcentagem);
}
