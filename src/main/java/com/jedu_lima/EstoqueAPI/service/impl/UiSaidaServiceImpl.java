package com.jedu_lima.EstoqueAPI.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;

public class UiSaidaServiceImpl extends JFrame {
	private static final long serialVersionUID = 1L;

	private UiProdutoServiceImpl uiService;
	private VerificacaoServiceImpl verificar;

	public interface UiSaidaServiceCallback {
		void onProdutosFetched(List<ProdutoSaida> listaDeSaidas);
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

	public void cadastrarSaida(ProdutoSaida saida, Long id, UiSaidaServiceCallback callback) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String url = "http://localhost:8080/v1/saida/" + id;
					String urlCallback = "http://localhost:8080/v1/saida";
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.registerModule(new JavaTimeModule());
					String json = objectMapper.writeValueAsString(saida);

					clientApi.postDadosParaApi(url, json);
					buscarDadosDaApi(urlCallback, callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void deletarSaida(Long id, UiSaidaServiceCallback callback) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String resposta = clientApi.deleteDadosDaApi("http://localhost:8080/v1/saida", id);

					if ("Deletado com sucesso.".equals(resposta)) {
						buscarDadosDaApi("http://localhost:8080/v1/saida", callback);
					} else {
						System.out.println("Falha ao deletar saida.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void salvarSaidas(JTextField tfIdDoProduto, JTextField tfQuantidadeSaida, UiSaidaServiceCallback callback) {

		uiService = new UiProdutoServiceImpl();
		verificar = new VerificacaoServiceImpl();
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Você tem certeza que deseja adicionar esta saida de produto?", "Confirmar Atualização",
				JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {

			Long idDoProduto = Long.parseLong(tfIdDoProduto.getText());
			int quantidadeSaida = Integer.parseInt(tfQuantidadeSaida.getText());
			ProdutoCadastro produto = uiService.buscarDadosPorId("http://localhost:8080/v1/produto", idDoProduto);

			if (produto == null) {
				JOptionPane.showMessageDialog(null, "Produto Não Encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
			} else {
				ProdutoSaida saida = new ProdutoSaida(produto, quantidadeSaida, null, LocalDate.now());
				if (verificar.verificarQuantidadeParaVenda(saida.getQuantidadeSaida(), produto.getQuantidadeTotal())) {
					JOptionPane.showMessageDialog(null, "Quantidade Insuficiente no Estoque", "Aviso",
							JOptionPane.WARNING_MESSAGE);
				} else {
					cadastrarSaida(saida, idDoProduto, callback);
					limparCampos(tfIdDoProduto, tfQuantidadeSaida);
				}
			}
		}
	}

	public void deletarSaidaSelecionada(JTable table, UiSaidaServiceCallback callback) {

		int confirmation = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar esta saida?",
				"Confirmar Atualização", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long saidaId = (Long) table.getValueAt(selectedRow, 0);
				deletarSaida(saidaId, callback);
			} else {
				System.out.println("Selecione uma entrada para deletar.");
			}
		}
	}

	public void limparCampos(JTextField tfIdDoProduto, JTextField tfQuantidadeSaida) {

		tfIdDoProduto.setText("");
		tfQuantidadeSaida.setText("");
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
