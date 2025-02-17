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
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;

public class UiEntradaServiceImpl extends JFrame {
	private static final long serialVersionUID = 1L;

	private UiProdutoServiceImpl uiService;

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
					ProdutoEntrada entradas[] = objectMapper.readValue(data, ProdutoEntrada[].class);

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

	public void cadastrarEntrada(ProdutoEntrada entrada, Long id, UiEntradaServiceCallback callback) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String url = "http://localhost:8080/v1/entrada/" + id;
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.registerModule(new JavaTimeModule());
					String json = objectMapper.writeValueAsString(entrada);

					String resposta = clientApi.postDadosParaApi(url, json);
					if ("Cadastrado com sucesso!".equals(resposta)) {
						buscarDadosDaApi("http://localhost:8080/v1/entrada", callback);
						JOptionPane.showMessageDialog(null, "Entrada Cadastrada", "Sucesso!",
								JOptionPane.WARNING_MESSAGE);
					} else {
						System.out.println("Falha ao cadastrar.");
						JOptionPane.showMessageDialog(null, "Falha Ao Cadastrar Entrada", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void deletarEntrada(Long id, UiEntradaServiceCallback callback) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String resposta = clientApi.deleteDadosDaApi("http://localhost:8080/v1/entrada", id);

					if ("Deletado com sucesso.".equals(resposta)) {
						JOptionPane.showMessageDialog(null, "Entrada Deletada", "Sucesso!",
								JOptionPane.WARNING_MESSAGE);
						buscarDadosDaApi("http://localhost:8080/v1/entrada", callback);
					} else {
						System.out.println("Falha ao deletar entrada.");
						JOptionPane.showMessageDialog(null, "Falha Ao Deletar Entrada!", "Aviso",
								JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void salvarEntrada(JTextField tfIdDoProduto, JTextField tfQuantidadeEntrada,
			UiEntradaServiceCallback callback) {

		uiService = new UiProdutoServiceImpl();
		int confirmation = JOptionPane.showConfirmDialog(null,
				"Você tem certeza que deseja adicionar esta entrada de produto?", "Confirmar Atualização",
				JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			Long idDoProduto = Long.parseLong(tfIdDoProduto.getText());
			int quantidadeEntrada = Integer.parseInt(tfQuantidadeEntrada.getText());
			ProdutoCadastro produto = uiService.buscarDadosPorId("http://localhost:8080/v1/produto", idDoProduto);

			if (produto == null) {
				JOptionPane.showMessageDialog(null, "Produto Não Encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
			} else {
				ProdutoEntrada entrada = new ProdutoEntrada(produto, quantidadeEntrada, LocalDate.now());
				cadastrarEntrada(entrada, idDoProduto, callback);
				limparCampos(tfIdDoProduto, tfQuantidadeEntrada);
			}
		}
	}

	public void deletarEntradaSelecionada(JTable table, UiEntradaServiceCallback callback) {

		int confirmation = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar esta entrada?",
				"Confirmar Atualização", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long entradaId = (Long) table.getValueAt(selectedRow, 0);
				deletarEntrada(entradaId, callback);
			} else {
				System.out.println("Selecione uma entrada para deletar.");
			}
		}
	}

	public void limparCampos(JTextField tfIdDoProduto, JTextField tfQuantidadeEntrada) {

		tfIdDoProduto.setText("");
		tfQuantidadeEntrada.setText("");
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
