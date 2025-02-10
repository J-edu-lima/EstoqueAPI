package com.jedu_lima.EstoqueAPI.service.impl;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;

public class UiEntradaServiceImpl extends JFrame {
	private static final long serialVersionUID = 1L;

	public interface UiEntradaServiceCallback {
		void onProdutosFetched(List<ProdutoEntrada> listaDeEntradas);
	}

	public void buscarDadosDaApi(String url, UiEntradaServiceCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String data = clientApi.getDadosDaApi(url);
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.registerModule(new JavaTimeModule());
					ProdutoEntrada[] entradas = objectMapper.readValue(data, ProdutoEntrada[].class);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							callback.onProdutosFetched(List.of(entradas));
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void atualizarTabela(JTable table, List<ProdutoEntrada> listaDeEntradas) {
		String[] colunas = { "ID", "ID do Produto", "Quantidade de Entrada", "Data de Entrada" };
		Object[][] dados = new Object[listaDeEntradas.size()][colunas.length];
		for (int i = 0; i < listaDeEntradas.size(); i++) {
			ProdutoEntrada entrada = listaDeEntradas.get(i);
			dados[i][0] = entrada.getId();
			dados[i][1] = entrada.getProduto().getId() + " - " + entrada.getProduto().getNome();
			dados[i][2] = entrada.getQuantidadeEntrada();
			dados[i][3] = entrada.getDataEntrada();
		}

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setDataVector(dados, colunas);
	}
}
