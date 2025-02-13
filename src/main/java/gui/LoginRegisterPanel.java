package gui;

import db.DbOperations;
import game.Board;
import game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginRegisterPanel extends JPanel {
    private final JLabel user = new JLabel("Username");
    private final JLabel pass = new JLabel("Password");
    private final JLabel message = new JLabel();
    private final JTextField userField = new JTextField();
    private final JPasswordField passField = new JPasswordField();
    private final JButton loginBtn = new JButton("Login");
    private final JButton registerBtn = new JButton("Register");
    private final DbOperations db = new DbOperations();

    public LoginRegisterPanel() {
        super(new GridLayout(4, 2));
        addComponents();
        setSize(400, 200);
        setVisible(true);
    }

    public void addComponents() {
        add(user);
        add(userField);
        add(pass);
        add(passField);
        add(loginBtn);
        add(registerBtn);
        add(message);
        loginBtn.addActionListener(this::login);
        registerBtn.addActionListener(this::register);
    }

    private void register(ActionEvent actionEvent) {
        Board board = new Board();
        String username = userField.getText();
        String pass = String.valueOf(passField.getPassword());
        if (db.insertUser(username, pass)) message.setText("You are now registered.");
        else message.setText("Username already exists.");

    }

    private void login(ActionEvent e) {
        Board board = new Board();
        String username = userField.getText();
        String pass = String.valueOf(passField.getPassword());
        if (db.login(username, pass)) {
            message.setText("You logged in!");
            Player player = new Player(username);
            board.addPlayer(player);
        } else message.setText("Login failed! Try again!");
    }


}
