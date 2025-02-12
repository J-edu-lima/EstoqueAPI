package com.jedu_lima.EstoqueAPI.service.impl;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;

public class UiSaidaServiceImpl extends JFrame {
	private static final long serialVersionUID = 1L;

	public interface UiSaidaServiceCallback {
		void onProdutosFetched(List<ProdutoSaida> listaDeEntradas);
	}

	public void buscarDadosDaApi(String url, UiSaidaServiceCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String data = clientApi.getDadosDaApi(url);
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.registerModule(new JavaTimeModule());
					ProdutoSaida saidas[] = objectMapper.readValue(data, ProdutoSaida[].class);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							callback.onProdutosFetched(List.of(saidas));
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void atualizarTabela(JTable table, List<ProdutoSaida> listaDeSaidas) {
		String[] colunas = { "ID", "ID do Produto", "Quantidade de Saída", "Total da Venda", "Data de Saida" };
		Object[][] dados = new Object[listaDeSaidas.size()][colunas.length];
		for (int i = 0; i < listaDeSaidas.size(); i++) {
			ProdutoSaida entrada = listaDeSaidas.get(i);
			dados[i][0] = entrada.getId();
			dados[i][1] = entrada.getProduto().getId() + " - " + entrada.getProduto().getNome();
			dados[i][2] = entrada.getQuantidadeSaida();
			dados[i][3] = entrada.getTotalVenda();
			dados[i][4] = entrada.getDataSaida();
		}

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setDataVector(dados, colunas);
	}
}
