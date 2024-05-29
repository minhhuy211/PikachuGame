package controller;

import java.awt.*;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LevelPanel extends JPanel {
    JButton btn;
    private Image backgroundImage;
    Main main;

    public LevelPanel() {
        setLayout(new BorderLayout());

        try {
            backgroundImage = ImageIO
                    .read(Objects.requireNonNull(getClass().getResource("/backgroundImage/anhGame.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.setPreferredSize(new Dimension(650, 400));
        add(createComponents(), BorderLayout.CENTER);
    }

    private JPanel createComponents() {

        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ ảnh nền nếu nó đã được load thành công
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            }
        };

        // Thiết lập GridBagContraints
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.insets.top = 0;
        c.insets.bottom = 0;

        // Khởi tạo các nút là các cấp độ của trò chơi.
        JPanel buttonPanel = new JPanel(new GridLayout(6, 8, 5, 5));
        // Trò chơi với 48 cấp độ
        for (int i = 0; i < 48; i++) {
            // Chạy vòng lặp for, mỗi nút sẽ được thiết lập giống nhau.
            btn = new JButton("" + (i + 1));
            btn.setFont(new Font("Roboto", Font.BOLD, 20));
            btn.setPreferredSize(new Dimension(60, 50));
            buttonPanel.add(btn);
            // Và mỗi nút khi nhấn vào sẽ chạy ra một giao diện trò chơi cho người dùng.
            btn.addActionListener(e -> {
                main = new Main();
                main.showMainFrame();
            });
        }

        // Define constraints for button panel
        c.gridy = 1;
        c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(50, 5, 50, 10); // Add padding around the buttons

        // // Add button panel to the panel
        panel.add(buttonPanel, c);

        return panel;
    }

}
