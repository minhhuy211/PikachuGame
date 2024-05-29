package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MainPanel extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;

	public static int MAX_TIME = 500;
	public int time = MAX_TIME;
	private int row = 4;
	private int col = 4;
	private int width = 900;
	private int height = 700;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	public JLabel lbScore;
	private JProgressBar progressTime;
	private JButton btnNewGame;

	private boolean pause = false;
	private boolean resume = false;

	public MainPanel() {
		setLayout(new BorderLayout());
		add(mainPanel = createMainPanel());
		setSize(width, height);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createGraphicsPanel(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.EAST);
		panel.add(createStatusPanel(), BorderLayout.PAGE_END);
		return panel;
	}

	private JPanel createGraphicsPanel() {
		graphicsPanel = new ButtonEvent(this, row, col);
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.gray);
		panel.add(graphicsPanel);
		return panel;
	}

	private JPanel createControlPanel() {
		lbScore = new JLabel("0");
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);

		JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
		panelLeft.add(new JLabel("Score:"));
		panelLeft.add(new JLabel("Time"));

		JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
		panelCenter.add(lbScore);
		panelCenter.add(progressTime);

		JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 6));
		panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

		JPanel panelControl = new JPanel(new BorderLayout(10, 10));
		panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
		panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
		panelControl.add(btnNewGame = createButton("New Game"), BorderLayout.PAGE_START);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("GOOD LUCK"));
		panel.add(panelControl, BorderLayout.PAGE_START);

		return panel;
	}

	private JPanel createStatusPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panel.setBackground(Color.lightGray);
		return panel;
	}

	private JButton createButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}

	public void newGame() {
		time = MAX_TIME;
		graphicsPanel.removeAll();
		mainPanel.add(createGraphicsPanel(), BorderLayout.CENTER);
		mainPanel.validate();
		mainPanel.setVisible(true);
		lbScore.setText("0");
	}

	public void showDialogNewGame(String message, String title, int t) {
		pause = true;
		resume = false;
		int select = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (select == 0) {
			pause = false;
			newGame();
		} else {
			if (t == 1) {
				System.exit(0);
			} else {
				resume = true;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNewGame) {
			showDialogNewGame("Your game hasn't done. Do you want to create a new game?", "Warning", 0);
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			progressTime.setValue((int) ((double) time / MAX_TIME * 100));
		}
	}

	public JProgressBar getProgressTime() {
		return progressTime;
	}

	public void setProgressTime(JProgressBar progressTime) {
		this.progressTime = progressTime;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isResume() {
		return resume;
	}

	public void setResume(boolean resume) {
		this.resume = resume;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public static void setMAX_TIME(int mAX_TIME) {
		MAX_TIME = mAX_TIME;
	}

	public int getTotalItem() {
		return this.col * this.row;
	}

	public int calculateScore(int item, int timeLeft) {
		return  item * 10 + this.time * 15;
	}
	
	
}
