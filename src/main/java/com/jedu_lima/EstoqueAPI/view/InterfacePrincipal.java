package com.jedu_lima.EstoqueAPI.view;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class InterfacePrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	public InterfacePrincipal() {
		setTitle("Interface Principal");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();

		JMenu menuProdutos = new JMenu("Produtos");
		menuBar.add(menuProdutos);
		JMenuItem itemProdutos = new JMenuItem("Produtos Cadastrados");
		menuProdutos.add(itemProdutos);
		itemProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfaceProdutos();
			}
		});

		JMenu menuEntradas = new JMenu("Entradas");
		menuBar.add(menuEntradas);
		JMenuItem itemEntrada = new JMenuItem("Entrada de Produtos");
		menuEntradas.add(itemEntrada);
		itemEntrada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfaceEntradas();
			}
		});

		JMenu menuSaidas = new JMenu("Saídas");
		menuBar.add(menuSaidas);
		JMenuItem itemSaidas = new JMenuItem("Saída de Produtos");
		menuSaidas.add(itemSaidas);
		itemSaidas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openInterfaceSaidas();
			}
		});

		JMenu menuSair = new JMenu("Sair");
		menuBar.add(menuSair);
		JMenuItem itemSair = new JMenuItem("Sair");
		menuSair.add(itemSair);
		itemSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		setJMenuBar(menuBar);

		ImageIcon imageIcon = new ImageIcon("C:\\java-libs\\gson-main\\5166970 1.png");
		Image image = imageIcon.getImage();
		Image scaledImage = image.getScaledInstance(256, 256, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel labelImagem = new JLabel(scaledIcon);

		setLayout(new BorderLayout());
		add(labelImagem, BorderLayout.CENTER);
	}

	private void openInterfaceEntradas() {
		setVisible(false);
		InterfaceEntradas telaEntradas = new InterfaceEntradas();
		telaEntradas.setVisible(true);
	}

	private void openInterfaceProdutos() {
		setVisible(false);
		InterfaceProdutos telaProdutos = new InterfaceProdutos();
		telaProdutos.setVisible(true);
	}

	private void openInterfaceSaidas() {
		setVisible(false);
		InterfaceSaidas telaSaidas = new InterfaceSaidas();
		telaSaidas.setVisible(true);
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