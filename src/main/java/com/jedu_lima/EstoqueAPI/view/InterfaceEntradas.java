package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.service.impl.UiEntradaServiceImpl;

public class InterfaceEntradas extends JFrame implements UiEntradaServiceImpl.UiEntradaServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiEntradaServiceImpl uiEntradaService;
	private JTextField tfIdDoProduto, tfQuantidadeEntrada;

	public InterfaceEntradas() {
		setTitle("Entrada de Produtos");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "ID do Produto", "Quantidade de Entrada", "Data de Entrada" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel painelEdicao = new JPanel(new GridLayout(3, 2));
		painelEdicao.setVisible(true);

		painelEdicao.add(new JLabel("ID do Produto:"));
		tfIdDoProduto = new JTextField();
		painelEdicao.add(tfIdDoProduto);

		painelEdicao.add(new JLabel("Quantidade de Entrada:"));
		tfQuantidadeEntrada = new JTextField();
		painelEdicao.add(tfQuantidadeEntrada);

		add(painelEdicao, BorderLayout.NORTH);

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

		JButton btnAdicionarEntrada = new JButton("Adicionar Entrada");
		painelBotoes.add(btnAdicionarEntrada);
		btnAdicionarEntrada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarEntrada();
			}
		});

		JButton btnDeletarEntrada = new JButton("Deletar Entrada");
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
	
		uiEntradaService = new UiEntradaServiceImpl();
	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	public void onProdutosFetched(List<ProdutoEntrada> listaDeEntradas) {
		uiEntradaService.atualizarTabela(table, listaDeEntradas);

	}

	private void buscarEntradas() {
		uiEntradaService.buscarDadosDaApi("http://localhost:8080/v1/entrada", InterfaceEntradas.this);
	}

	public void salvarEntrada() {
		uiEntradaService.salvarEntrada(tfIdDoProduto, tfQuantidadeEntrada, InterfaceEntradas.this);
	}

	private void deletarEntradaSelecionado() {
		uiEntradaService.deletarEntradaSelecionada(table, InterfaceEntradas.this);
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
