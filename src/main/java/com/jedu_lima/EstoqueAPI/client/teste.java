package com.jedu_lima.EstoqueAPI.client;

import com.jedu_lima.EstoqueAPI.service.impl.VerificacaoServiceImpl;

public class teste {

	public static void main(String[] args) {
		VerificacaoServiceImpl ver = new VerificacaoServiceImpl();
		Double numero = -23.00;

		System.out.print(ver.verificarNumeroNegativo(numero));

	}

}
