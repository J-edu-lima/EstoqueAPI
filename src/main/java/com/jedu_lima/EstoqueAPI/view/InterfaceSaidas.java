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

import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;
import com.jedu_lima.EstoqueAPI.service.impl.UiSaidaServiceImpl;

public class InterfaceSaidas extends JFrame implements UiSaidaServiceImpl.UiSaidaServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiSaidaServiceImpl uiSaidaService;

	public InterfaceSaidas() {
		setTitle("Saída de Produtos");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "ID do Produto", "Quantidade de Saída", "Total da Venda", "Data de Saida" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton btnBuscarSaidas = new JButton("Buscar Saídas");
		painelBotoes.add(btnBuscarSaidas);
		btnBuscarSaidas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarSaidas();
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
		uiSaidaService = new UiSaidaServiceImpl();
	}

	@Override
	public void onProdutosFetched(List<ProdutoSaida> listaDeEntradas) {
		uiSaidaService.atualizarTabela(table, listaDeEntradas);
	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	private void buscarSaidas() {
		uiSaidaService.buscarDadosDaApi("http://localhost:8080/v1/saida", InterfaceSaidas.this);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				InterfaceSaidas frame = new InterfaceSaidas();
				frame.setVisible(true);
			}
		});
	}
}
