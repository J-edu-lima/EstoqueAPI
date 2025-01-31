package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jedu_lima.EstoqueAPI.client.ClientApi;
import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;

public class InterfaceProdutos extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;

	public InterfaceProdutos() {

		setTitle("Produtos Cadastrados");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "Código De Barras", "Nome", "Preço De Compra", "Quantidade", "Preço De Venda", "Porcentagem" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JButton btnBuscarProdutos = new JButton("Buscar Produtos");
		add(btnBuscarProdutos, BorderLayout.SOUTH);
		btnBuscarProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarDadosDaApi("http://localhost:8080/v1/produto");
			}
		});
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

	private void atualizarTabela(List<ProdutoCadastro> listaDeProdutos) {

		String[] colunas = { "ID", "Código De Barras", "Nome", "Preço De Compra", "Quantidade", "Preço De Venda", "Porcentagem" };
		Object[][] dados = new Object[listaDeProdutos.size()][colunas.length];
		for (int i = 0; i < listaDeProdutos.size(); i++) {
			ProdutoCadastro produto = listaDeProdutos.get(i);
			dados[i][0] = produto.getId(); //
			dados[i][1] = produto.getCodigoDeBarras();
			dados[i][2] = produto.getNome();
			dados[i][3] = produto.getValorCompra();
			dados[i][4] = produto.getQuantidadeTotal();
			dados[i][5] = produto.getValorSugerido();
			dados[i][6] = produto.getPorcentagemSobreVenda();
		}

		AbstractTableModel modelo = new DefaultTableModel(dados, colunas);
		table.setModel(modelo);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				InterfaceProdutos frame = new InterfaceProdutos();
				frame.setVisible(true);
			}
		});
	}
}