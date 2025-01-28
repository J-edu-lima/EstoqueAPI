package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class InterfaceProdutos extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;

	public InterfaceProdutos() {

		setTitle("Produtos Cadastrados");
		setSize(500, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "Código De Barras", "Nome", "Preço De Compra", "Quantidade", "Preço De Venda",
				"Porcentagem" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JButton btnBuscarDados = new JButton("Buscar Dados");
		add(btnBuscarDados, BorderLayout.SOUTH);
		btnBuscarDados.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarDadosDaApi("http://localhost:8080/v1/produto");
			}
		});
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

	public void buscarDadosDaApi(String url) {

		tableModel.setRowCount(0);
	}
}