package com.jedu_lima.EstoqueAPI.service.impl;

import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public class UiServiceImpl extends JFrame {
	private static final long serialVersionUID = 1L;

	public interface UiServiceCallback {
		void onProdutosFetched(List<ProdutoCadastro> listaDeProdutos);
	}

	public void cadastrarProduto(ProdutoCadastro produto, UiServiceCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String url = "http://localhost:8080/v1/produto";
					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(produto);

					clientApi.postDadosParaApi(url, json);
					buscarDadosDaApi(url, callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void buscarDadosDaApi(String url, UiServiceCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String data = clientApi.getDadosDaApi(url);
					ObjectMapper objectMapper = new ObjectMapper();
					ProdutoCadastro[] produtos = objectMapper.readValue(data, ProdutoCadastro[].class);

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							callback.onProdutosFetched(List.of(produtos));
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public ProdutoCadastro buscarDadosPorId(String url, Long id) {
		ProdutoCadastro produto = null;
		ClientApi clientApi = new ClientApi();
		String data;
		try {
			data = clientApi.getDadosDaApi(url + "/" + id);
			ObjectMapper objectMapper = new ObjectMapper();
			produto = objectMapper.readValue(data, ProdutoCadastro.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return produto;
	}

	public void deletarProduto(Long id, UiServiceCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String resposta = clientApi.deleteDadosDaApi("http://localhost:8080/v1/produto", id);

					if ("Deletado com sucesso.".equals(resposta)) {
						buscarDadosDaApi("http://localhost:8080/v1/produto", callback);
					} else {
						System.out.println("Falha ao deletar produto.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void limparCampos(JTextField tfCodigoBarras, JTextField tfNome, JTextField tfPrecoCompra,
			JTextField tfQuantidade, JTextField tfPorcentagem) {
		tfCodigoBarras.setText("");
		tfNome.setText("");
		tfPrecoCompra.setText("");
		tfQuantidade.setText("");
		tfPorcentagem.setText("");
	}

	public void atualizarProduto(ProdutoCadastro produto, UiServiceCallback callback) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String url = "http://localhost:8080/v1/produto/" + produto.getId();
					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(produto);
					clientApi.putDadosParaApi(url, json);
					buscarDadosDaApi("http://localhost:8080/v1/produto", callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void atualizarTabela(JTable table, List<ProdutoCadastro> listaDeProdutos) {
		String[] colunas = { "ID", "Código De Barras", "Nome", "Preço De Compra", "Quantidade", "Preço De Venda",
				"Porcentagem" };
		Object[][] dados = new Object[listaDeProdutos.size()][colunas.length];
		for (int i = 0; i < listaDeProdutos.size(); i++) {
			ProdutoCadastro produto = listaDeProdutos.get(i);
			dados[i][0] = produto.getId();
			dados[i][1] = produto.getCodigoDeBarras();
			dados[i][2] = produto.getNome();
			dados[i][3] = produto.getValorCompra();
			dados[i][4] = produto.getQuantidadeTotal();
			dados[i][5] = produto.getValorSugerido();
			dados[i][6] = produto.getPorcentagemSobreVenda();
		}

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setDataVector(dados, colunas);
	}
}
