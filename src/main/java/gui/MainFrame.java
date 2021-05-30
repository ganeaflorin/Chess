package gui;

import game.Board;
import game.Move;
import game.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class MainFrame extends JFrame {
    private final JFrame game;
    private BoardPanel panel;
    private final Dimension dimension = new Dimension(1200, 800);
    private final Board board = new Board();
    private final Move move = new Move(board);
    private Square firstSquare;
    private Square secondSquare;
    private Square checkedSquare;
    public static final Color clickedSquare = new Color(0, 51, 102);
    public static final Color checkedSquareColor = new Color(139, 0, 0);
    public static final Color checkMateKing = new Color(20, 20, 20);
    private final CoordinatesPanel coordinates = new CoordinatesPanel();
    private final GamePanel gamePanel = new GamePanel(this, board);

    public MainFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        game = new JFrame("Chess");
        game.setSize(dimension);
        game.add(setBoardPanel(), BorderLayout.WEST);
        game.add(gamePanel, BorderLayout.EAST);
        gamePanel.updateScore(board);
        setBoardPieces();
        pack();
        game.setVisible(true);
    }

    public JPanel setBoardPanel() {
        JPanel boardPanel = new JPanel(new BorderLayout());
        panel = new BoardPanel();
        boardPanel.add(panel, BorderLayout.CENTER);
        boardPanel.add(coordinates.getDigits(), BorderLayout.WEST);
        boardPanel.add(coordinates.getLetters(), BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(800, 800));
        return boardPanel;
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
                        handleFirstSqNull(squarePanel);
                    } else {
                        handleSecondSq(squarePanel);
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

    private void handleSecondSq(SquarePanel squarePanel) {
        checkedSquare = null;
        secondSquare = board.getBoard()[squarePanel.getLine()][squarePanel.getColumn()];
        if (board.isWhiteTurn() == firstSquare.getPiece().isWhite()) {
            if (move.makeMove(firstSquare.getX(), firstSquare.getY(), secondSquare.getX(), secondSquare.getY())) {
                try {
                    gamePanel.updateCapturedPieces(move.getLastCapturedPiece());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                board.changeTurn();
                gamePanel.updateTurn(board.isWhiteTurn());
            }
            handleCheckmate();
        }
        if (move.isCheck() && !move.isCheckMate())
            checkedSquare = move.getCheckedSquare();
        updateBoardImages();
        firstSquare = null;
        secondSquare = null;
    }

    private void handleFirstSqNull(SquarePanel squarePanel) {
        firstSquare = board.getBoard()[squarePanel.getLine()][squarePanel.getColumn()];
        if (!firstSquare.isEmpty() && board.isWhiteTurn() == firstSquare.getPiece().isWhite())
            squarePanel.setBackground(clickedSquare);
        if (firstSquare.isEmpty())
            firstSquare = null;
        else if (board.isWhiteTurn() == firstSquare.getPiece().isWhite())
            showPotentialMoves(move.availableMoves(firstSquare));
    }

    private void handleCheckmate() {
        if (move.isCheckMate()) {
            gamePanel.setButtonVisible();
            checkedSquare = move.getCheckedSquare();
            colorCheckmate();
            board.updateScore(!checkedSquare.getPiece().isWhite());
            gamePanel.updateScore(board);
        }
    }

    private void colorCheckmate() {
        for (SquarePanel squarePanel : panel.getBoardPanels()) {
            if (checkedSquare != null && squarePanel.getLine() == checkedSquare.getX() && squarePanel.getColumn() == checkedSquare.getY()) {
                squarePanel.setBackground(checkMateKing);
            } else squarePanel.setColor();
        }
    }

    public void setSquaresNull() {
        this.checkedSquare = null;
        firstSquare = null;
        secondSquare = null;
    }

    public void updateBoardImages() {
        for (SquarePanel squarePanel : panel.getBoardPanels()) {
            if (!move.isCheckMate())
                if (checkedSquare != null && squarePanel.getLine() == checkedSquare.getX() && squarePanel.getColumn() == checkedSquare.getY()) {
                    squarePanel.setBackground(checkedSquareColor);
                } else
                    squarePanel.setColor();
            squarePanel.setImage(board);
            squarePanel.setBorder(null);
        }
    }

}


