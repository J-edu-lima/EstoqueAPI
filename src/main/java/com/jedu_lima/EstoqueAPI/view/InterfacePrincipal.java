package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class InterfacePrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	public InterfacePrincipal() {
		setTitle("Interface Principal");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		JButton btnProdutos = new JButton("Produtos Cadastrados");
		btnProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfaceProdutos();
			}
		});

		JButton btnCadastro = new JButton("Cadastrar Produtos");
		btnCadastro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfaceCadastroProduto();
			}
		});

		painelBotoes.add(btnProdutos);
		painelBotoes.add(btnCadastro);

		add(painelBotoes, BorderLayout.SOUTH);
	}

	private void openInterfaceProdutos() {
		setVisible(false);
		InterfaceProdutos telaProdutos = new InterfaceProdutos();
		telaProdutos.setVisible(true);
	}

	private void openInterfaceCadastroProduto() {
		setVisible(false);
		InterfaceCadastroProduto cadastroProdutos = new InterfaceCadastroProduto();
		cadastroProdutos.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				InterfacePrincipal tela = new InterfacePrincipal();
				tela.setVisible(true);
			}
		});
	}
}