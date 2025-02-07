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

import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.service.impl.UiEntradaServiceImpl;

public class InterfaceEntradas extends JFrame implements UiEntradaServiceImpl.UiEntradaServiceCallback {
	static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiEntradaServiceImpl uiService;

	public InterfaceEntradas() {
		setTitle("Produtos Cadastrados");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "ID do Produto", "Quantidade de Entrada", "Data de Entrada" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton btnBuscarEntradas = new JButton("Buscar Entradas");
		painelBotoes.add(btnBuscarEntradas);
		btnBuscarEntradas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarEntradas();
			}
		});
		add(painelBotoes, BorderLayout.SOUTH);
	}

	private void buscarEntradas() {
		uiService.buscarDadosDaApi("http://localhost:8080/v1/entrada", InterfaceEntradas.this);
	}

	public void onProdutosFetched(List<ProdutoEntrada> listaDeEntradas) {
		uiService.atualizarTabela(table, listaDeEntradas);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				InterfaceEntradas frame = new InterfaceEntradas();
				frame.setVisible(true);
			}
		});
	}
}
