package gui;

import game.Board;
import org.jboss.jandex.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginRegister extends JFrame {
    private LoginRegisterPanel firstUser = new LoginRegisterPanel();
    private LoginRegisterPanel second = new LoginRegisterPanel();
    private JPanel startPanel;
    private JLabel startMessage;

    public LoginRegister() {
        super("Login Register");
        setLayout(new BorderLayout(0, 25));
        setStartPanel();
        add(startPanel, BorderLayout.CENTER);
        add(firstUser, BorderLayout.NORTH);
        add(second, BorderLayout.SOUTH);
        this.setSize(400, 400);
        setVisible(true);
    }

    private void startGame(ActionEvent actionEvent) {
        Board board = new Board();
        if (board.hasEnoughPlayers()) {
            this.setVisible(false);
            MainFrame game = new MainFrame();
            board.printPlayers();
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
        gbc.gridx = 0;
        gbc.gridy = 1;
        startPanel.add(startMessage);
    }
}
