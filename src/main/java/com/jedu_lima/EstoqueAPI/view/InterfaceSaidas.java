package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
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
import com.jedu_lima.EstoqueAPI.entity.ProdutoSaida;
import com.jedu_lima.EstoqueAPI.service.impl.UiSaidaServiceImpl;
import com.jedu_lima.EstoqueAPI.service.impl.UiServiceImpl;

public class InterfaceSaidas extends JFrame implements UiSaidaServiceImpl.UiSaidaServiceCallback {
	private static final long serialVersionUID = 1L;

	private JTable table;
	private DefaultTableModel tableModel;
	private UiServiceImpl uiService;
	private UiSaidaServiceImpl uiSaidaService;
	private JTextField tfIdDoProduto, tfQuantidadeSaida;

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

		JPanel painelEdicao = new JPanel(new GridLayout(3, 2));
		painelEdicao.setVisible(true);

		painelEdicao.add(new JLabel("ID do Produto:"));
		tfIdDoProduto = new JTextField();
		painelEdicao.add(tfIdDoProduto);

		painelEdicao.add(new JLabel("Quantidade de Saida:"));
		tfQuantidadeSaida = new JTextField();
		painelEdicao.add(tfQuantidadeSaida);

		add(painelEdicao, BorderLayout.NORTH);

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

		JButton btnAdicionarSaidas = new JButton("Adicionar Saida");
		painelBotoes.add(btnAdicionarSaidas);
		btnAdicionarSaidas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarSaidas();
			}
		});

		JButton btnDeletarSaida = new JButton("Deletar Saida");
		painelBotoes.add(btnDeletarSaida);
		btnDeletarSaida.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletarSaidaSelecionada();
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
		uiService = new UiServiceImpl();
	}

	@Override
	public void onProdutosFetched(List<ProdutoSaida> listaDeSaidas) {
		uiSaidaService.atualizarTabela(table, listaDeSaidas);
	}

	private void openInterfacePrincipal() {
		setVisible(false);
		InterfacePrincipal telaPrincipal = new InterfacePrincipal();
		telaPrincipal.setVisible(true);
	}

	private void buscarSaidas() {
		uiSaidaService.buscarDadosDaApi("http://localhost:8080/v1/saida", InterfaceSaidas.this);
	}

	private void salvarSaidas() {
		int confirmation = JOptionPane.showConfirmDialog(this,
				"Você tem certeza que deseja adicionar esta saida de produto?", "Confirmar Atualização",
				JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {

			Long idDoProduto = Long.parseLong(tfIdDoProduto.getText());
			int quantidadeSaida = Integer.parseInt(tfQuantidadeSaida.getText());
			BigDecimal valor = null;
			ProdutoCadastro produto = uiService.buscarDadosPorId("http://localhost:8080/v1/produto", idDoProduto);
			ProdutoSaida saida = new ProdutoSaida(produto, quantidadeSaida, valor, LocalDate.now());

			uiSaidaService.cadastrarSaida(saida, idDoProduto, InterfaceSaidas.this);
			uiSaidaService.limparCampos(tfIdDoProduto, tfQuantidadeSaida);
		}
	}

	private void deletarSaidaSelecionada() {
		int confirmation = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja deletar esta entrada?",
				"Confirmar Atualização", JOptionPane.YES_NO_OPTION);
		if (confirmation == JOptionPane.YES_OPTION) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Long saidaId = (Long) table.getValueAt(selectedRow, 0);
				uiSaidaService.deletarSaida(saidaId, InterfaceSaidas.this);
			} else {
				System.out.println("Selecione uma entrada para deletar.");
			}
		}
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
