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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.service.impl.UiService;

public class InterfaceCadastroProduto extends JFrame implements UiService.UiServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private JTextField tfCodigoBarras, tfNome, tfPrecoCompra, tfQuantidade, tfPorcentagem;
	private UiService uiService;

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
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		
		JButton btnCadastrarProduto = new JButton("Cadastrar Produto");
		btnCadastrarProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarProduto();
			}
		});

		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfacePrincipal();
			}
		});
		
		painelBotoes.add(btnCadastrarProduto);
		painelBotoes.add(btnVoltar);
		add(painelBotoes, BorderLayout.SOUTH);
		
		uiService = new UiService();
	}

	public void salvarProduto() {
		Long codigoBarras = Long.parseLong(tfCodigoBarras.getText());
		String nome = tfNome.getText();
		BigDecimal precoCompra = new BigDecimal(tfPrecoCompra.getText());
		int quantidade = Integer.parseInt(tfQuantidade.getText());
		double porcentagem = Double.parseDouble(tfPorcentagem.getText());

		ProdutoCadastro novoProduto = new ProdutoCadastro(codigoBarras, nome, precoCompra, quantidade, porcentagem);
		uiService.cadastrarProduto(novoProduto, this);
	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	@Override
	public void onProdutosFetched(List<ProdutoCadastro> listaDeProdutos) {
		uiService.atualizarTabela(table, listaDeProdutos);
	}

	public static void main(String[] args) {
		InterfaceCadastroProduto tela = new InterfaceCadastroProduto();
		tela.setVisible(true);
	}
}