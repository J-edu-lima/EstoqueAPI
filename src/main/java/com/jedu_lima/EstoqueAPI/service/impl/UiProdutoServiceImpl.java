package com.jedu_lima.EstoqueAPI.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public class UiProdutoServiceImpl extends JFrame {
	private static final long serialVersionUID = 1L;

	private VerificacaoServiceImpl verificar;

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

					String resposta = clientApi.postDadosParaApi(url, json);
					buscarDadosDaApi(url, callback);

					if ("Cadastrado com sucesso!".equals(resposta)) {
						buscarDadosDaApi("http://localhost:8080/v1/produto", callback);
					} else {
						System.out.println("Falha ao cadastrar.");
						JOptionPane.showMessageDialog(null,
								"Falha Ao Cadastrar Produto: Verifique Se Não Existem"
										+ " Produtos Registrados Com Esse Nome ou Código.",
								"Aviso", JOptionPane.WARNING_MESSAGE);
					}
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
						JOptionPane.showMessageDialog(null,
								"Falha Ao Deletar Produto: Verifique Se Não Existem"
										+ " Entradas ou Saidas Relacionadas a Esse ID.",
								"Aviso", JOptionPane.WARNING_MESSAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void deletarProdutoSelecionado(JTable table, UiServiceCallback callback) {

		verificar = new VerificacaoServiceImpl();
		int confirmation = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja deletar este produto?",
				"Confirmar Deleção", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long produtoId = (Long) table.getValueAt(selectedRow, 0);

				deletarProduto(produtoId, callback);
			} else {
				System.out.println("Selecione um produto para deletar.");
			}
		}
	}

	public void salvarProduto(JTextField tfCodigoBarras, JTextField tfNome, JTextField tfPrecoCompra,
			JTextField tfQuantidade, JTextField tfPorcentagem, UiServiceCallback callback)
			throws SQLIntegrityConstraintViolationException {

		verificar = new VerificacaoServiceImpl();
		int confirmation = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja cadastrar este produto?",
				"Confirmar Cadastro", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			Long codigoBarras = Long.parseLong(tfCodigoBarras.getText());
			String nome = tfNome.getText();
			BigDecimal precoCompra = new BigDecimal(tfPrecoCompra.getText());
			int quantidade = Integer.parseInt(tfQuantidade.getText());
			Double porcentagem = Double.parseDouble(tfPorcentagem.getText());

			if (verificar.verificarNumeroNegativo(precoCompra, porcentagem, quantidade)) {
				JOptionPane.showMessageDialog(null, "Dados Inválidos", "Aviso", JOptionPane.WARNING_MESSAGE);
			} else {
				ProdutoCadastro novoProduto = new ProdutoCadastro(codigoBarras, nome, precoCompra, quantidade,
						porcentagem);
				cadastrarProduto(novoProduto, callback);
				limparCampos(tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem);
			}
		}
	}

	public void atualizarProdutoSelecionado(JTable table, JTextField tfCodigoBarras, JTextField tfNome,
			JTextField tfPrecoCompra, JTextField tfQuantidade, JTextField tfPorcentagem, UiServiceCallback callback) {

		verificar = new VerificacaoServiceImpl();
		int confirmation = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja atualizar este produto?",
				"Confirmar Atualização", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long produtoId = (Long) table.getValueAt(selectedRow, 0);
				Long codigoBarras = Long.parseLong(tfCodigoBarras.getText());
				String nome = tfNome.getText();
				BigDecimal precoCompra = new BigDecimal(tfPrecoCompra.getText());
				Integer quantidade = Integer.parseInt(tfQuantidade.getText());
				Double porcentagem = Double.parseDouble(tfPorcentagem.getText());

				ProdutoCadastro produto = new ProdutoCadastro();
				produto.setId(produtoId);
				produto.setCodigoDeBarras(codigoBarras);
				produto.setNome(nome);
				produto.setValorCompra(precoCompra);
				produto.setQuantidadeTotal(quantidade);
				produto.setPorcentagemSobreVenda(porcentagem);

				atualizarProduto(produto, callback);
				limparCampos(tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem);
			}
		} else {
			System.out.println("Selecione um produto para atualizar.");
		}
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
