package controller;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Main {
	MainFrame frame;

	public Main() {
		frame = new MainFrame();
		MyTimeCount timeCount = new MyTimeCount();
		timeCount.start();
		new Thread(frame).start();
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
