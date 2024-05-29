package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class IntroductionPanel extends JPanel {
    private JButton exit;
    private JButton exit1;
    private JPanel panel1;
    private JTextArea content1;
    private JPanel contentImage1;
    private JButton nextTo;
    private JPanel panel2;
    private JTextArea content2;
    private JPanel contentImage2;
    private JButton backTo;
    private CardLayout cardLayout;
    private JPanel cardsPanel;
    private Image image1;
    private Image image2;

    public IntroductionPanel() {
        this.setSize(650, 400);
        exit = new JButton("Exit");
        exit1 = new JButton("Exit");
        JPanel panelOutner1 = new JPanel(new BorderLayout());
        JPanel panelOutner2 = new JPanel(new BorderLayout());

        content1 = new JTextArea(5, 15) {};
        content1.setLineWrap(true);
        content1.setWrapStyleWord(true);
        content1.setOpaque(false); // Make the JTextArea transparent
        content1.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(content1);

        contentImage1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image1 != null) {
                    int imgWidth = image1.getWidth(this);
                    int imgHeight = image1.getHeight(this);
                    int newWidth = getWidth();
                    int newHeight = (imgHeight * newWidth) / imgWidth;
                    g.drawImage(image1, 0, 0, newWidth, newHeight, this);
                }
            }
        };
        contentImage1.setPreferredSize(new Dimension(600, 600));
        nextTo = new JButton("Next");
        panel1 = new JPanel(new BorderLayout());
        panel1.add(scrollPane1, BorderLayout.NORTH);
        panel1.add(contentImage1, BorderLayout.CENTER);
        panel1.add(nextTo, BorderLayout.EAST);

        content2 = new JTextArea(5, 15);
        content2.setLineWrap(true);
        content2.setWrapStyleWord(true);
        content2.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(content2);

        contentImage2  = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image2 != null) {
                    int imgWidth = image2.getWidth(this);
                    int imgHeight = image2.getHeight(this);
                    int newWidth = getWidth();
                    int newHeight = (imgHeight * newWidth) / imgWidth;
                    g.drawImage(image2, 0, 0, newWidth, newHeight, this);
                }
            }
        };
        contentImage2.setPreferredSize(new Dimension(600, 600));
        backTo = new JButton("Back");
        panel2 = new JPanel(new BorderLayout());
        panel2.add(scrollPane2, BorderLayout.NORTH);
        panel2.add(contentImage2, BorderLayout.CENTER);
        panel2.add(backTo, BorderLayout.EAST);

        panelOutner1.add(exit, BorderLayout.WEST);
        panelOutner1.add(panel1, BorderLayout.CENTER);
        panelOutner2.add(exit1, BorderLayout.WEST);
        panelOutner2.add(panel2, BorderLayout.CENTER);

        // Create the CardLayout
        cardLayout = new CardLayout();
        cardsPanel = new JPanel(cardLayout);

        cardsPanel.add(panelOutner1, "panel1");
        cardsPanel.add(panelOutner2, "panel2");
        this.add(cardsPanel);
        cardLayout.show(cardsPanel, "panel1");

        // Add action listeners for the buttons
        nextTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "panel2");
            }
        });

        backTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardsPanel, "panel1");
            }
        });

        // Load content from file and image
        loadContentFromFile("guide/guidePikachu.txt");
        loadImage("image/picture1.PNG");
        loadImage("image/picture2.PNG");

        this.setVisible(true);
    }

    private void loadContentFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder contentBuilder1 = new StringBuilder();
            StringBuilder contentBuilder2 = new StringBuilder();
            String line;
            boolean isFirstStep = true;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Bước 1")) {
                    contentBuilder1.append(line).append("\n");
                } else if (line.startsWith("Bước 2")) {
                    contentBuilder2.append(line).append("\n");
                    isFirstStep = false;
                } else if (isFirstStep) {
                    contentBuilder1.append(line).append("\n");
                } else {
                    contentBuilder2.append(line).append("\n");
                }
            }
            content1.setText(contentBuilder1.toString());
            content2.setText(contentBuilder2.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadImage(String imagePath) {
        if (imagePath.endsWith("picture2.PNG")) {
            image2 = new ImageIcon(imagePath).getImage();
            contentImage2.repaint();
        } else {
            image1 = new ImageIcon(imagePath).getImage();
            contentImage1.repaint();
        }
        content1.repaint();
    }

    public JButton getExit() {
        return exit;
    }

    public JButton getExit1() {
        return exit1;
    }

    public JTextArea getContent1() {
        return content1;
    }

    public JTextArea getContent2() {
        return content2;
    }
}
