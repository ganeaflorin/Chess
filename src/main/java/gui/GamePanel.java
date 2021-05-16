package gui;

import game.Board;
import game.Piece;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel {
    private JPanel capturedBlackPieces;
    private JPanel capturedWhitePieces;
    private JPanel turn;
    private JPanel playerInfo;
    private JButton rematchBtn = new JButton("Play again");
    private int whiteIndex;
    private int blackIndex;
    private GridBagConstraints gbc = new GridBagConstraints();
    private Board board;
    private MainFrame game;

    public GamePanel(MainFrame game, Board board) {
        super(new BorderLayout());
        this.board = board;
        this.game = game;
        setTurnIcon();
        setCapturedPieces();
        setPlayerInfo();
        setPreferredSize(new Dimension(360, 800));
        JPanel capturedPiecesContainer = new JPanel(new BorderLayout());
        capturedPiecesContainer.add(capturedBlackPieces, BorderLayout.SOUTH);
        capturedPiecesContainer.add(capturedWhitePieces, BorderLayout.NORTH);
        add(capturedPiecesContainer, BorderLayout.WEST);
        add(playerInfo, BorderLayout.CENTER);
        rematchBtn.setVisible(false);
        rematchBtn.addActionListener(this::rematch);
    }

    private void rematch(ActionEvent actionEvent) {
        JPanel tempWhitePieces = (JPanel) capturedWhitePieces.getComponent(0);
        tempWhitePieces.removeAll();
        JPanel tempBlackPieces = (JPanel) capturedWhitePieces.getComponent(0);
        tempBlackPieces.removeAll();
        board.resetBoard();
        board.setWhiteTurn(true);
        turn.getComponent(1).setBackground(Color.white);
        game.setSquaresNull();
        game.updateBoardImages();
        rematchBtn.setVisible(false);
    }

    public void setButtonVisible() {
        rematchBtn.setVisible(true);
    }

    public void setPlayerInfo() {
        playerInfo = new JPanel(new BorderLayout());
        playerInfo.setPreferredSize(new Dimension(200, 100));
    }

    public void updateScore(Board board) {
        gbc.insets = new Insets(0, 0, 0, 0);
        playerInfo.removeAll();
        JLabel turnText = new JLabel("Turn:");
        Player player1 = board.getPlayers().get(0);
        Player player2 = board.getPlayers().get(1);
        String score;
        if (player1.isWhite())
            score = "(White) " + player1.getName() + " " + player1.getScore() + " - " + player2.getScore() + " " + player2.getName() + " (Black)";
        else
            score = "(Black) " + player1.getName() + " " + player1.getScore() + " - " + player2.getScore() + " " + player2.getName() + " (White)";

        JLabel scoreBoard = new JLabel(score);
        scoreBoard.setPreferredSize(new Dimension(200, 50));
        turnText.setPreferredSize(new Dimension(50, 50));
        JPanel jPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        jPanel.setPreferredSize(new Dimension(200, 200));
        jPanel.add(scoreBoard, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;

        gbc.insets = new Insets(20, 0, 0, 100);
        jPanel.add(turn, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
//        gbc.insets = new Insets(0, 0, 0, 100);
        jPanel.add(rematchBtn, gbc);
        playerInfo.add(jPanel, BorderLayout.CENTER);
//        scoreBoard.setText(score);
    }

    public void setTurnIcon() {
        turn = new JPanel(new GridBagLayout());
        JPanel tempTurn = new JPanel();
        JLabel turnText = new JLabel("Turn");
        tempTurn.setPreferredSize(new Dimension(50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 20);
        turn.add(turnText, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        turn.add(tempTurn, gbc);
        turn.setPreferredSize(new Dimension(200, 50));
        tempTurn.setBackground(Color.white);
        tempTurn.setBorder(BorderFactory.createLineBorder(Color.black, 1, true));
    }

    public void setCapturedPieces() {
        capturedWhitePieces = new JPanel(new GridBagLayout());
        JPanel tempWhitePieces = new JPanel(new GridLayout(9, 2));
        tempWhitePieces.setPreferredSize(new Dimension(50, 400));
        capturedWhitePieces.add(tempWhitePieces);
        capturedBlackPieces = new JPanel(new GridBagLayout());
        JPanel tempBlackPieces = new JPanel(new GridLayout(9, 2));
        tempBlackPieces.setPreferredSize(new Dimension(50, 400));
        capturedBlackPieces.add(tempBlackPieces);

    }

    public void updateCapturedPieces(Piece piece) throws IOException {
        if (piece != null) {
            String path = piece.getPath();
            BufferedImage image = ImageIO.read(new File(path));
            Image imageResize = image.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            if (piece.isWhite()) {
                JPanel jp = (JPanel) capturedWhitePieces.getComponent(0);
                jp.add(new JLabel(new ImageIcon(imageResize)), whiteIndex);
                whiteIndex++;
            } else {
                JPanel jp = (JPanel) capturedBlackPieces.getComponent(0);
                jp.add(new JLabel(new ImageIcon(imageResize)), blackIndex);
                blackIndex++;
            }
        }

    }

    public void updateTurn(boolean isWhite) {
        if ((isWhite)) {
            turn.getComponent(1).setBackground(Color.white);
        } else {
            turn.getComponent(1).setBackground(Color.black);
        }
    }

}
