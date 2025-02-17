package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.service.impl.UiProdutoServiceImpl;

public class InterfaceProdutos extends JFrame implements UiProdutoServiceImpl.UiServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiProdutoServiceImpl uiService;
	private JTextField tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem;

	public InterfaceProdutos() {

		setTitle("Produtos Cadastrados");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		String[] colunas = { "ID", "Código De Barras", "Nome", "Preço De Compra", "Quantidade", "Preço De Venda",
				"Porcentagem" };
		tableModel = new DefaultTableModel(colunas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel painelEdicao = new JPanel(new GridLayout(7, 2));
		painelEdicao.setVisible(true);

		painelEdicao.add(new JLabel("Código de Barras:"));
		tfCodigoBarras = new JTextField();
		painelEdicao.add(tfCodigoBarras);

		painelEdicao.add(new JLabel("Nome:"));
		tfNome = new JTextField();
		painelEdicao.add(tfNome);

		painelEdicao.add(new JLabel("Preço de Compra:"));
		tfPrecoCompra = new JTextField();
		painelEdicao.add(tfPrecoCompra);

		painelEdicao.add(new JLabel("Quantidade:"));
		tfQuantidade = new JTextField();
		painelEdicao.add(tfQuantidade);

		painelEdicao.add(new JLabel("Porcentagem:"));
		tfPorcentagem = new JTextField();
		painelEdicao.add(tfPorcentagem);

		add(painelEdicao, BorderLayout.NORTH);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow != -1) {

						Long codigoBarras = (Long) table.getValueAt(selectedRow, 1);
						String nome = (String) table.getValueAt(selectedRow, 2);
						BigDecimal precoCompra = (BigDecimal) table.getValueAt(selectedRow, 3);
						Integer quantidade = (Integer) table.getValueAt(selectedRow, 4);
						Double porcentagem = (Double) table.getValueAt(selectedRow, 6);

						tfCodigoBarras.setText(codigoBarras.toString());
						tfNome.setText(nome);
						tfPrecoCompra.setText(precoCompra.toString());
						tfQuantidade.setText(quantidade.toString());
						tfPorcentagem.setText(porcentagem.toString());
					}
				}
			}
		});

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton btnBuscarProdutos = new JButton("Buscar Produtos");
		painelBotoes.add(btnBuscarProdutos);
		btnBuscarProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarProdutos();
				uiService.limparCampos(tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem);
			}
		});

		JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
		painelBotoes.add(btnCadastrarProduto);
		btnCadastrarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					salvarProduto();
				} catch (SQLIntegrityConstraintViolationException e1) {
					e1.getErrorCode();
				}
			}
		});

		JButton btnDeletarProduto = new JButton("Deletar Produto");
		painelBotoes.add(btnDeletarProduto);
		btnDeletarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarProdutoSelecionado();
			}
		});

		JButton btnAtualizarProduto = new JButton("Atualizar Produto");
		painelBotoes.add(btnAtualizarProduto);
		btnAtualizarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizarProdutoSelecionado();
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

		painelBotoes.setPreferredSize(new Dimension(800, 80));
		painelBotoes.setMinimumSize(new Dimension(600, 60));
		add(painelBotoes, BorderLayout.PAGE_END);

		uiService = new UiProdutoServiceImpl();

	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	public void onProdutosFetched(List<ProdutoCadastro> listaDeProdutos) {
		uiService.atualizarTabela(table, listaDeProdutos);
	}

	private void buscarProdutos() {
		uiService.buscarDadosDaApi("http://localhost:8080/v1/produto", InterfaceProdutos.this);
	}

	private void deletarProdutoSelecionado() {
		uiService.deletarProdutoSelecionado(table, InterfaceProdutos.this);
	}

	public void salvarProduto() throws SQLIntegrityConstraintViolationException {
		uiService.salvarProduto(tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem,
				InterfaceProdutos.this);

	}

	private void atualizarProdutoSelecionado() {
		uiService.atualizarProdutoSelecionado(table, tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem,
				InterfaceProdutos.this);
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