package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ButtonEvent extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private int row;
	private int col;
	private int score = 0;
	private int bound = 2;
	private int size = 50;
	private Point p1 = null;
	private Point p2 = null;
	private JButton[][] btn;
	private Controller algorithm;
	private Color backGroundColor = Color.lightGray;
	private MainPanel frame;
	private PointLine line;
	private int item;

	public ButtonEvent(MainPanel frame, int row, int col) {
		this.frame = frame;
		this.row = row + 2;
		this.col = col + 2;
		item = row * col / 2;

		setLayout(new GridLayout(row, col, bound, bound));
		setBackground(backGroundColor);
		setPreferredSize(new Dimension((size + bound) * col, (size + bound) * row));
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setAlignmentY(JPanel.CENTER_ALIGNMENT);

		newGame();

	}

	public void newGame() {
		algorithm = new Controller(this.frame, this.row, this.col);
		addArrayButton();

	}

	private void addArrayButton() {
		btn = new JButton[row][col];
		for (int i = 1; i < row - 1; i++) {
			for (int j = 1; j < col - 1; j++) {
				btn[i][j] = createButton(i + "," + j);
				Icon icon = getIcon(algorithm.getMatrix()[i][j]);
				btn[i][j].setIcon(icon);
				add(btn[i][j]);
			}
		}
	}

	private Icon getIcon(int index) {
		int width = 48, height = 48;
		Image image = new ImageIcon(getClass().getResource("/icon/" + index + ".png")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;

	}
	
	private JButton createButton(String action) {
		JButton btn = new JButton();
		btn.setActionCommand(action);
		btn.setBorder(null);
		btn.addActionListener(this);
		return btn;
	}

	public void execute(Point p1, Point p2) {
		System.out.println("Detele");
		setDisable(btn[p1.x][p1.y]);
		setDisable(btn[p2.x][p2.y]);
	}

	public void setDisable(JButton btn) {
		btn.setIcon(null);
		btn.setBackground(backGroundColor);
		btn.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnIndex = e.getActionCommand();
		int indexDot = btnIndex.lastIndexOf(",");
		int x = Integer.parseInt(btnIndex.substring(0, indexDot));
		int y = Integer.parseInt(btnIndex.substring(indexDot + 1, btnIndex.length()));

		if (p1 == null) {
			p1 = new Point(x, y);
			btn[p1.x][p1.y].setBorder(new LineBorder(Color.red));
		} else {
			p2 = new Point(x, y);
			
			// Tính năng tính điểm và thông báo điểm sau màn chơi
			scoreFeatureHandler(p1, p2);
			
			
			p1 = null;
			p2 = null;
		}
	}
	
	private void set2PointNull(Point p1, Point p2) {
		btn[p1.x][p1.y].setBorder(null);
		p1 = null;
		p2 = null;
	}
	
	private void setScoreLabel(int score) {
		line = null;
		frame.lbScore.setText(score + "");
	}
	
	private void decreaseItem() {
		item--;
	}
	
	private void increaseScore() {
		algorithm.getMatrix()[p1.x][p1.y] = 0;
		algorithm.getMatrix()[p2.x][p2.y] = 0;
		score += 10;
	}
	
	private boolean checkEndGame() {
		return item == 0;
	}
	
	private int getFinalScore() {
		return frame.calculateScore(item, frame.time);
	}
	
	private void endGame(int score) {
		frame.showDialogNewGame("You are Winer!\nYour final score: " + score + "\nDo you want play again?", "Win", 1);
	}
	
	private void scoreFeatureHandler(Point p1, Point p2) {
		//1.Hệ thống kiểm tra nếu hai hình ảnh (hai đối tượng Point) 
		//	được chọn giống nhau và có thể kết nối:
		line = algorithm.checkTwoPoint(p1, p2);
		//1.1. Nếu có thể kết nối được
		if (line != null) {
			
			//1.1.1. Điểm số của người chơi tăng l
			increaseScore();
			//1.1.2. Giảm số lượng ảnh trong màn chơi
			decreaseItem();
			//1.1.3. Hai hình ảnh (hai đối tượng Point) được xóa khỏi màn chơi.
			execute(p1, p2);
			//1.1.4. Hệ thống cập nhật và hiện thị điểm số lên giao diện MainPanel
			setScoreLabel(score);
		}
		//1.2 Nếu không kế nối được
		//1.2.1. Gán giá trị null có hai đối tượng Point nhận vào.
		set2PointNull(p1, p2);
		
		//2. Kiểm tra điều kiện kết thúc màn chơi (Số hình ảnh đã hết):
		if (checkEndGame()) { 
			//2.1. Hệ thống tính điểm dựa trên thời gian còn lại.
			int finalScore = getFinalScore();
			//2.2. Hệ thống hiển thị màn hình thông báo màn chơi kết thúc 
			// 		và tổng điểm của người chơi. 
			endGame(finalScore);
		}
	}
}
