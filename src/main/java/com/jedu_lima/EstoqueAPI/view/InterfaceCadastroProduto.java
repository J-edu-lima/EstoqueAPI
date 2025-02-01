package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.core.io.BigDecimalParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public class InterfaceCadastroProduto extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem;

	public InterfaceCadastroProduto() {
		setTitle("Cadastro de Produto");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "Código De Barras", "Nome", "Preço De Compra", "Quantidade", "Preço De Venda",
				"Porcentagem" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel painelCadastro = new JPanel(new GridLayout(7, 2));
		painelCadastro.add(new JLabel("Código de Barras:"));
		tfCodigoBarras = new JTextField();
		painelCadastro.add(tfCodigoBarras);

		painelCadastro.add(new JLabel("Nome:"));
		tfNome = new JTextField();
		painelCadastro.add(tfNome);

		painelCadastro.add(new JLabel("Preço de Compra:"));
		tfPrecoCompra = new JTextField();
		painelCadastro.add(tfPrecoCompra);

		painelCadastro.add(new JLabel("Quantidade:"));
		tfQuantidade = new JTextField();
		painelCadastro.add(tfQuantidade);

		painelCadastro.add(new JLabel("Porcentagem:"));
		tfPorcentagem = new JTextField();
		painelCadastro.add(tfPorcentagem);

		add(painelCadastro, BorderLayout.NORTH);

		JPanel painelBotoes = new JPanel();
		JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
		painelBotoes.add(btnCadastrarProduto);
		add(painelBotoes, BorderLayout.SOUTH);

		btnCadastrarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarProduto();
			}
		});
	}

	public void cadastrarProduto() {

		Long codigoBarras = Long.parseLong(tfCodigoBarras.getText());
		String nome = tfNome.getText();
		BigDecimal precoCompra = BigDecimalParser.parse(tfPrecoCompra.getText());
		int quantidade = Integer.parseInt(tfQuantidade.getText());
		double porcentagem = Double.parseDouble(tfPorcentagem.getText());

		ProdutoCadastro novoProduto = new ProdutoCadastro(codigoBarras, nome, precoCompra, quantidade, porcentagem);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(novoProduto);

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ClientApi clientApi = new ClientApi();
						String url = "http://localhost:8080/v1/produto";
						clientApi.postDadosParaApi(url, json);

						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								buscarDadosDaApi("http://localhost:8080/v1/produto");
							}
						});
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}).start();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void buscarDadosDaApi(String url) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					ClientApi clientApi = new ClientApi();
					String data = clientApi.getDadosDaApi(url);
					ObjectMapper objectMapper = new ObjectMapper();
					ProdutoCadastro[] produtos = objectMapper.readValue(data, ProdutoCadastro[].class);
					List<ProdutoCadastro> listaDeProdutos = new ArrayList<>();
					for (ProdutoCadastro produto : produtos) {
						listaDeProdutos.add(produto);
					}

					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							atualizarTabela(listaDeProdutos);
						}
					});
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}).start();
	}

	
	public void atualizarTabela(List<ProdutoCadastro> listaDeProdutos) {
		tableModel.setRowCount(0);
		for (ProdutoCadastro produto : listaDeProdutos) {
			tableModel.addRow(new Object[] { produto.getId(), produto.getCodigoDeBarras(), produto.getNome(),
					produto.getValorCompra(), produto.getQuantidadeTotal(), produto.getValorSugerido(),
					produto.getPorcentagemSobreVenda() });
		}
	}

	public static void main(String[] args) {
		InterfaceCadastroProduto tela = new InterfaceCadastroProduto();
		tela.setVisible(true);
	}
}
