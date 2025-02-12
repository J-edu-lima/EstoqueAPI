package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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
import javax.swing.table.DefaultTableModel;

import com.jedu_lima.EstoqueAPI.entity.ProdutoCadastro;
import com.jedu_lima.EstoqueAPI.entity.ProdutoEntrada;
import com.jedu_lima.EstoqueAPI.service.impl.UiEntradaServiceImpl;
import com.jedu_lima.EstoqueAPI.service.impl.UiServiceImpl;

public class InterfaceEntradas extends JFrame implements UiEntradaServiceImpl.UiEntradaServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiEntradaServiceImpl uiEntradaService;
	private UiServiceImpl uiService;
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
		uiService = new UiServiceImpl();
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
		int confirmation = JOptionPane.showConfirmDialog(this,
				"Você tem certeza que deseja adicionar esta entrada de produto?", "Confirmar Atualização",
				JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {

			Long idDoProduto = Long.parseLong(tfIdDoProduto.getText());
			int quantidadeEntrada = Integer.parseInt(tfQuantidadeEntrada.getText());
			ProdutoCadastro produto = uiService.buscarDadosPorId("http://localhost:8080/v1/produto", idDoProduto);
			ProdutoEntrada entrada = new ProdutoEntrada(produto, quantidadeEntrada, LocalDate.now());

			uiEntradaService.cadastrarEntrada(entrada, idDoProduto, InterfaceEntradas.this);
			uiEntradaService.limparCampos(tfIdDoProduto, tfQuantidadeEntrada);
		}
	}

	private void deletarEntradaSelecionado() {
		int confirmation = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja deletar esta entrada?",
				"Confirmar Atualização", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long entradaId = (Long) table.getValueAt(selectedRow, 0);
				uiEntradaService.deletarEntrada(entradaId, InterfaceEntradas.this);
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
