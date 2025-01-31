package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class InterfacePrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	public InterfacePrincipal() {

		setTitle("Interface Principal");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JButton btnProdutos = new JButton("Produtos Cadastrados");
		add(btnProdutos, BorderLayout.CENTER);
		btnProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfaceProdutos();
			}
		});
	}

	private void openInterfaceProdutos() {
		setVisible(false);
		InterfaceProdutos telaProdutos = new InterfaceProdutos();
		telaProdutos.setVisible(true);
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