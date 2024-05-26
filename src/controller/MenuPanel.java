package controller;

import java.awt.*;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class MenuPanel extends JPanel {
	JButton btnNewGame, btnExit, btnGuide;
	private Image backgroundImage;

	public MenuPanel() {
		setLayout(new BorderLayout());

		try {
			backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/backgroundImage/anhGame.jpg")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setPreferredSize(new Dimension(650, 400));
		add(createComponents(), BorderLayout.CENTER);
	}



	private JPanel createComponents() {
		// Custom JPanel that paints the background image
		JPanel panel = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Vẽ ảnh nền nếu nó đã được load thành công
				Graphics2D graphics2D = (Graphics2D) g;
				graphics2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
			}
		};

		GridBagConstraints c = new GridBagConstraints();

		// Configure title label
		JLabel lbl = new JLabel("PIKACHU GAME");
		lbl.setVerticalTextPosition(JLabel.CENTER);
		lbl.setHorizontalTextPosition(JLabel.CENTER);
		lbl.setFont(new Font("Roboto", Font.BOLD, 60));
		lbl.setForeground(Color.green);

		// Define constraints for title label
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0; // Move title to row 0 (top)
		c.insets = new Insets(10, 10, 10, 10); // Add padding around the title
		// Adjust vertical spacing to position the title within the red rectangle
		c.insets.top = 0; // Set top padding to control vertical placement
		c.insets.bottom = 30; // Set bottom padding to control vertical placement

		// Add title label to the panel
		panel.add(lbl, c);

		// Configure button panel
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
		btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Roboto", Font.BOLD, 20));
		btnNewGame.setPreferredSize(new Dimension(150, 50));
		buttonPanel.add(btnNewGame);
		btnGuide = new JButton("Introduction");
		btnGuide.setFont(new Font("Roboto", Font.BOLD, 20));
		btnGuide.setPreferredSize(new Dimension(150, 50));
		buttonPanel.add(btnGuide);
		btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Roboto", Font.BOLD, 20));
		btnExit.setPreferredSize(new Dimension(150, 50));
		buttonPanel.add(btnExit);

		// Define constraints for button panel
		c.gridy = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 150, 10); // Add padding around the buttons

		// Add button panel to the panel
		panel.add(buttonPanel, c);

		return panel;
	}
}
