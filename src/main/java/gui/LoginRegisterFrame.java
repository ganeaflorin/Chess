package gui;

import game.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginRegisterFrame extends JFrame {
    private final LoginRegisterPanel firstUser = new LoginRegisterPanel();
    private final LoginRegisterPanel secondUser = new LoginRegisterPanel();
    private JPanel startPanel;
    private JLabel startMessage;

    public LoginRegisterFrame() {
        super("Login Register");
        setLayout(new BorderLayout(0, 25));
        setStartPanel();
        add(startPanel, BorderLayout.CENTER);
        add(firstUser, BorderLayout.NORTH);
        add(secondUser, BorderLayout.SOUTH);
        this.setSize(400, 400);
        setVisible(true);
    }

    private void startGame(ActionEvent actionEvent) {
        Board board = new Board();
        if (board.hasEnoughPlayers()) {
            this.setVisible(false);
            MainFrame game = new MainFrame();
//            board.printPlayers();
        } else startMessage.setText("We need 2 users to play!");
    }

    private void setStartPanel() {
        startPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        JButton startBtn = new JButton("Start game");
        startBtn.setPreferredSize(new Dimension(100, 50));
        startBtn.addActionListener(this::startGame);
        startMessage = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 0;
        startPanel.add(startBtn);
        gbc.gridy = 1;
        startPanel.add(startMessage);
    }
}
