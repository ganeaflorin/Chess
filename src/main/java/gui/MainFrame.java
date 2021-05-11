package gui;

import game.Board;
import game.Move;
import game.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class MainFrame {
    private final JFrame game;
    private final BoardPanel panel;
    private final Dimension dimension = new Dimension(800, 800);
    private final Board board;
    private Move move;
    private Square firstSquare;
    private Square secondSquare;
    private Square checkedSquare;
    private boolean whiteTurn = true;
    public static final Color clickedSquare = new Color(0, 51, 102);
    public static final Color checkedSquareColor = new Color(139, 0, 0);

    public MainFrame() {
        board = new Board();
        move = new Move(board);
        move = new Move(board);
        game = new JFrame("Chess");
        game.setSize(dimension);
        panel = new BoardPanel();
        game.add(panel);
        setBoardPieces();
        game.setVisible(true);
    }

    public void setBoardPieces() {
        for (SquarePanel squarePanel : panel.getBoardPanels()) {
            squarePanel.setImage(board);
            addListener(squarePanel);
        }
    }

    public void showPotentialMoves(List<Square> potentialMoves) {
        for (Square square : potentialMoves)
            for (SquarePanel squarePanel : panel.getBoardPanels()) {
                if (squarePanel.getLine() == square.getX() && squarePanel.getColumn() == square.getY()) {
                    squarePanel.setBackground(clickedSquare);
                    squarePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                }
            }
    }

    public void addListener(SquarePanel squarePanel) {
        squarePanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isRightMouseButton(e)) {
                    firstSquare = null;
                    secondSquare = null;
                    updateBoardImages();
                }
                if (isLeftMouseButton(e)) {
                    if (firstSquare == null) {
                        firstSquare = board.getBoard()[squarePanel.getLine()][squarePanel.getColumn()];
                        if (!firstSquare.isEmpty() && whiteTurn == firstSquare.getPiece().isWhite())
                            squarePanel.setBackground(clickedSquare);
                        if (firstSquare.isEmpty())
                            firstSquare = null;
                        else if (whiteTurn == firstSquare.getPiece().isWhite())
                            showPotentialMoves(move.availableMoves(firstSquare));
                    } else {
                        checkedSquare = null;
                        secondSquare = board.getBoard()[squarePanel.getLine()][squarePanel.getColumn()];
                        if (whiteTurn == firstSquare.getPiece().isWhite()) {
                            if (move.makeMove(firstSquare.getX(), firstSquare.getY(), secondSquare.getX(), secondSquare.getY()))
                                changeTurn();
//                            move.isCheck();
                            move.isCheckMate();
                        }
                        checkedSquare = move.getCheckedSquare();
                        updateBoardImages();
                        firstSquare = null;
                        secondSquare = null;
                    }
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }


            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public void updateBoardImages() {
        for (SquarePanel squarePanel : panel.getBoardPanels()) {
            if (checkedSquare != null && squarePanel.getLine() == checkedSquare.getX() && squarePanel.getColumn() == checkedSquare.getY()) {
                squarePanel.setBackground(checkedSquareColor);
            } else
                squarePanel.setColor();
            squarePanel.setImage(board);
//            squarePanel.setColor();
            squarePanel.setBorder(null);
        }
    }

    public void changeTurn() {
        whiteTurn = !whiteTurn;
    }
}


