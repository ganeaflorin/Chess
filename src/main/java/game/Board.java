package game;

import game.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final Square[][] board = new Square[8][8];
    private boolean whiteTurn = true;
    private static List<Player> players = new ArrayList<>();

    public Board() {
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++)
                board[lineIndex][columnIndex] = new Square(lineIndex, columnIndex, null);
        setBlackPieces();
        setWhitePieces();
    }

    public void setBlackPieces() {
        boolean isWhite = false;
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                if (lineIndex == 0) setFirstRankPieces(lineIndex, columnIndex, isWhite);
                if (lineIndex == 1)
                    board[lineIndex][columnIndex].setPiece(new Pawn(isWhite));
            }

    }

    public void setWhitePieces() {
        boolean isWhite = true;
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                if (lineIndex == 7) setFirstRankPieces(lineIndex, columnIndex, isWhite);
                if (lineIndex == 6)
                    board[lineIndex][columnIndex].setPiece(new Pawn(isWhite));
            }
    }

    public void setFirstRankPieces(int lineIndex, int columnIndex, boolean isWhite) {
        Square square = board[lineIndex][columnIndex];
        if (columnIndex == 0 || columnIndex == 7)
            square.setPiece(new Rook(isWhite));
        if (columnIndex == 1 || columnIndex == 6)
            square.setPiece(new Knight(isWhite));
        if (columnIndex == 2 || columnIndex == 5)
            square.setPiece(new Bishop(isWhite));
        if (columnIndex == 3)
            square.setPiece(new Queen(isWhite));
        if (columnIndex == 4)
            square.setPiece(new King(isWhite));
    }


    public Square[][] getBoard() {
        return board;
    }

    public void changeTurn() {
        whiteTurn = !whiteTurn;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void setWhiteTurn(boolean whiteTurn) {
        this.whiteTurn = whiteTurn;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean hasEnoughPlayers() {
        if (players.size() == 2) {
            assignRandomColours();
            return true;
        }
        return false;
    }

    public void assignRandomColours() {
        Random rand = new Random();
        if (rand.nextInt() % 2 == 0) {
            players.get(0).setWhite(true);
            players.get(1).setWhite(false);

        } else {
            players.get(1).setWhite(true);
            players.get(0).setWhite(false);
        }
    }

    public void resetBoard() {
        for (int lineIndex = 0; lineIndex < 8; lineIndex++) {
            for (int columnIndex = 0; columnIndex < 8; columnIndex++)
                board[lineIndex][columnIndex].setEmpty();
        }
        setBlackPieces();
        setWhitePieces();
        switchColors();
    }

    public void printPlayers() {
        for (Player player : players) {
            System.out.println(player.getName() + "score: " + player.getScore() + " white: " + player.isWhite());
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void updateScore(boolean isWhiteWinner) {
        for (Player player : players)
            if (player.isWhite() == isWhiteWinner) {
                player.incrementScore();
            }
    }

    public void switchColors() {
        for (Player player : players) {
            player.setWhite(!player.isWhite());
        }
    }

}
