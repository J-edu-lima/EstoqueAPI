package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.service.impl.UiService;

public class InterfaceProdutos extends JFrame implements UiService.UiServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiService uiService;

	public InterfaceProdutos() {
		setTitle("Produtos Cadastrados");
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

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton btnBuscarProdutos = new JButton("Buscar Produtos");
		btnBuscarProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				uiService.buscarDadosDaApi("http://localhost:8080/v1/produto", InterfaceProdutos.this);
			}
		});

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfacePrincipal();
			}
		});

		JButton btnDeletarProduto = new JButton("Deletar Produto");
		btnDeletarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarProdutoSelecionado();
			}
		});

		painelBotoes.add(btnBuscarProdutos);
		painelBotoes.add(btnVoltar);
		painelBotoes.add(btnDeletarProduto);
		add(painelBotoes, BorderLayout.SOUTH);

		uiService = new UiService();
	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	public void onProdutosFetched(List<ProdutoCadastro> listaDeProdutos) {
		uiService.atualizarTabela(table, listaDeProdutos);
	}

	private void deletarProdutoSelecionado() {
		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
			Long produtoId = (Long) table.getValueAt(selectedRow, 0);
			uiService.deletarProduto(produtoId, InterfaceProdutos.this);
		} else {
			System.out.println("Selecione um produto para deletar.");
		}
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