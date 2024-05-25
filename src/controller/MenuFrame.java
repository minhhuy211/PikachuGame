package controller;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class MenuFrame extends JFrame{
	JButton btnNewGame, btnExit;
	public MenuFrame() {
		setTitle("Pikachu Game");
		this.setLayout(new BorderLayout());
		this.add(createComponents(), BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	private JPanel createComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		JLabel lbl = new JLabel("PIKACHU GAME");
		lbl.setVerticalTextPosition(JLabel.CENTER);
		lbl.setHorizontalTextPosition(JLabel.CENTER);
		lbl.setFont(new Font("Roboto", Font.BOLD, 60));
		panel.add(lbl);
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2, 1));
		btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Roboto", Font.BOLD, 20));
		temp.add(btnNewGame);
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Roboto", Font.BOLD, 20));
		temp.add(btnExit);
		panel.add(temp);
		return panel;
	}
}
