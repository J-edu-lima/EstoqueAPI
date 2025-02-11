package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.service.impl.UiEntradaServiceImpl;

public class InterfaceEntradas extends JFrame implements UiEntradaServiceImpl.UiEntradaServiceCallback {
	private static final long serialVersionUID = 1L;

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

		JButton btnDeletarEntrada = new JButton("Deletar Produto");
		painelBotoes.add(btnDeletarEntrada);
		btnDeletarEntrada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarEntradaSelecionado();
			}
		});

		JButton btnVoltar = new JButton("Voltar");
		painelBotoes.add(btnVoltar);
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfacePrincipal();
			}
		});

		add(painelBotoes, BorderLayout.SOUTH);
		uiService = new UiEntradaServiceImpl();
	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	public void onProdutosFetched(List<ProdutoEntrada> listaDeEntradas) {
		uiService.atualizarTabela(table, listaDeEntradas);

	}

	private void buscarEntradas() {
		uiService.buscarDadosDaApi("http://localhost:8080/v1/entrada", InterfaceEntradas.this);
	}

	private void deletarEntradaSelecionado() {
		int confirmation = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja deletar esta entrada?",
				"Confirmar Atualização", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long entradaId = (Long) table.getValueAt(selectedRow, 0);
				uiService.deletarEntrada(entradaId, InterfaceEntradas.this);
			} else {
				System.out.println("Selecione uma entrada para deletar.");
			}
		}
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
