package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame implements ActionListener, Runnable {
	
	 private static final long serialVersionUID = 1L;
	
	private int MAX_TIME = 500;
	public int time = MAX_TIME;
	private int row = 12;
	private int col = 12;
	private int width = 900;
	private int height = 700;
	private ButtonEvent graphicsPanel;
	private JPanel mainPanel;
	public JLabel lbScore;
	private JProgressBar progressTime;
	private JButton btnNewGame;

	private boolean pause = false;
	private boolean resume = false;

	public MainFrame() {
		add(mainPanel = createMainPanel());
		setTitle("Pokemon Game");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);

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
		// Tạo JLabel lbScore với giá trị ban đầu là 0
		lbScore = new JLabel("0");
		progressTime = new JProgressBar(0, 100);
		progressTime.setValue(100);

		// Tạo Panel chứa Score và Time
		JPanel panelLeft = new JPanel(new GridLayout(2, 1, 5, 5));
		panelLeft.add(new JLabel("Score:"));
		panelLeft.add(new JLabel("Time"));

		JPanel panelCenter = new JPanel(new GridLayout(2, 1, 5, 5));
		panelCenter.add(lbScore);
		panelCenter.add(progressTime);

		JPanel panelScoreAndTime = new JPanel(new BorderLayout(5, 6));
		panelScoreAndTime.add(panelLeft, BorderLayout.WEST);
		panelScoreAndTime.add(panelCenter, BorderLayout.CENTER);

		// Tạo Panel mới chứa panelScoreAndTime và nút New Game
		JPanel panelControl = new JPanel(new BorderLayout(10, 10));
		panelControl.setBorder(new EmptyBorder(10, 3, 5, 3));
		panelControl.add(panelScoreAndTime, BorderLayout.CENTER);
		panelControl.add(btnNewGame = createButton("New Game"), BorderLayout.PAGE_START);

		// Set BorderLayout để panelControl xuất hiện ở đầu trang
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder("GOOD LUCK"));
		panel.add(panelControl, BorderLayout.PAGE_START);

		return panel;
	}
	
	  // create status panel container author
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

	

}