package controller;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.*;

public class Main {
	static MainPanel mainPanel;
	static MenuPanel menuPanel;
	static JFrame frame;
	static CardLayout cardLayout;
	static JPanel cardPanel;

	public Main() {
		frame = new JFrame("Game Menu");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		menuPanel = new MenuPanel();
		mainPanel = new MainPanel();

		cardPanel.add(menuPanel, "MenuPanel");
		cardPanel.add(mainPanel, "MainPanel");

		frame.add(cardPanel);
		frame.setVisible(true);
		frame.pack();
		// Ensure the MenuPanel is shown first
		cardLayout.show(cardPanel, "MenuPanel");


		menuPanel.btnNewGame.addActionListener(e -> {

			showMainFrame();
		});
		menuPanel.btnExit.addActionListener(e -> {

			System.exit(0);
		});
	}

	private void showMainFrame() {
		cardLayout.show(cardPanel, "MainPanel");
		new Thread(mainPanel).start();
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Main();

		File file = new File("GamePikachu.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		clip.start();
	}

	class MyTimeCount extends Thread {
		public void run() {
			while (true) {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (mainPanel.isPause()) {
					if (mainPanel.isResume()) {
						mainPanel.time--;
					}
				} else {
					mainPanel.time--;
				}
				if (mainPanel.time == 0) {
					mainPanel.showDialogNewGame("Full Time\nDo you want play again?", "LOSE", 1);
				}
			}
		}
	}
}
