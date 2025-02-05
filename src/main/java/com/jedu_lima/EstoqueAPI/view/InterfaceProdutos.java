package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.service.impl.UiService;

public class InterfaceProdutos extends JFrame implements UiService.UiServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiService uiService;
	private JTextField tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem;

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

		JButton btnAtualizarProduto = new JButton("Atualizar Produto");
		btnAtualizarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				atualizarProdutoSelecionado();
			}
		});

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

		painelBotoes.add(btnBuscarProdutos);
		painelBotoes.add(btnDeletarProduto);
		painelBotoes.add(btnAtualizarProduto);
		painelBotoes.add(btnVoltar);

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

	private void atualizarProdutoSelecionado() {
		int confirmation = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja atualizar este produto?",
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

				uiService.atualizarProduto(produto, new UiService.UiServiceCallback() {
					@Override
					public void onProdutosFetched(List<ProdutoCadastro> listaDeProdutos) {
						uiService.atualizarTabela(table, listaDeProdutos);
						uiService.limparCampos(tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem);
					}
				});
			}
		} else {
			System.out.println("Selecione um produto para atualizar.");
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