package controller;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Game {
	MainPanel frame;
	MenuPanel menu;
	public Game() {
		menu = new MenuPanel();
		menu.btnNewGame.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				newGame();
				menu.setVisible(false);
			}
		});
		menu.btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
		
	}
	
	private void newGame() {
		this.frame = new MainPanel();
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
		new Thread(frame).start();		
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Game();
		
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
					Thread.sleep(600);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (frame.isPause()) {
					if (frame.isResume()) {
						frame.time--;
					}
				} else {
					frame.time--;
				}
				if (frame.time == 0) {
					frame.showDialogNewGame("Full Time\nDo you want play again?", "LOSE", 1);
				}
			}
		}
	}
	

}
