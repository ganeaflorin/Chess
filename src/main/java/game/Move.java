package game;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private Square[][] board;

    public Move(Board board) {
        this.board = board.getBoard();
    }

    //true daca mutarea e valida
    //false daca nu
    public boolean validateMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
        boolean isAttacking = false;
        if (!endSquare.isEmpty())
            isAttacking = true;
        //daca e gol nu putem muta
        if (startSquare.isEmpty()) return false;
        //daca vrem sa mutam peste o piesa proprie
        if (!endSquare.isEmpty() && startSquare.getPiece().isWhite() == endSquare.getPiece().isWhite()) return false;
        //daca nu e empty
        //verificam daca mutarea e valida
        if (!startSquare.getPiece().isValidMove(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex, isAttacking))
            return false;
        //daca putem ajunge pe patratul respectiv (nu e alta piesa in drum)
        return isReacheable(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
    }

    public Square getCheckedSquare() {
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                List<Square> availableMoves = availableMoves(board[lineIndex][columnIndex]);
                for (Square square : availableMoves)
                    if (!square.isEmpty() && square.getPiece().toString().equals("King")) {
                        System.out.println("CHECKED!!!");
                        return square;
                    }
            }
        return null;
    }

    public List<Square> availableMoves(Square square) {
        List<Square> availableSquares = new ArrayList<>();
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                Square candidateSquare = board[lineIndex][columnIndex];
                if (validateMove(square.getX(), square.getY(), lineIndex, columnIndex)) {
                    availableSquares.add(candidateSquare);
                }
            }
        return availableSquares;
    }

    public boolean makeMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
        Square auxEndSquare = endSquare;
        //TODO: BUG CAND O PIESA INCEARCA SA SE MUTE, DAR JUCATORUL RAMANE IN SAH (PIESA PE CARE INCERCAM SA O MUTAM SE DUBLEAZA)
        //TODO: CHECK FOR CHECKMATE
        //TODO: PAWN PROMOTION
        //TODO: CASTLE
        Square checkedSquare = null;
        if (validateMove(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex)) {
            endSquare.setPiece(startSquare.getPiece());
            startSquare.setEmpty();
            if ((checkedSquare = getCheckedSquare()) != null && checkedSquare.getPiece().isWhite() == endSquare.getPiece().isWhite()) {
                startSquare.setPiece(endSquare.getPiece());
                endSquare.setPiece(auxEndSquare.getPiece());
                return false;
            }
            endSquare.getPiece().setWasMoved(true);
            return true;
        }
        return false;
    }

    public boolean isReacheable(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];

        switch (startSquare.getPiece().toString()) {
            case "Rook":
                return isReacheableRook(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Queen":
                return isReacheableQueen(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Bishop":
                return isReacheableBishop(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Pawn":
                return isReacheablePawn(startLineIndex, startColumnIndex, endLineIndex);
            default:
                return true;
        }
    }

    private boolean isReacheablePawn(int startLineIndex, int starColumnIndex, int endLineIndex) {
        if (startLineIndex == endLineIndex + 2 && !board[startLineIndex - 1][starColumnIndex].isEmpty())
            return false;
        if (startLineIndex == endLineIndex - 2 && !board[startLineIndex + 1][starColumnIndex].isEmpty())
            return false;
        return true;
    }

    private boolean isReacheableBishop(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        if (startLineIndex - startColumnIndex == endLineIndex - endColumnIndex) {
            if (startLineIndex > endLineIndex) {
                int columnIndex = endColumnIndex + 1;
                for (int lineIndex = endLineIndex + 1; lineIndex < startLineIndex; lineIndex++) {
                    if (!board[lineIndex][columnIndex].isEmpty())
                        return false;
                    columnIndex++;
                }
            } else {
                int columnIndex = startColumnIndex + 1;
                for (int lineIndex = startLineIndex + 1; lineIndex < endLineIndex; lineIndex++) {
                    if (!board[lineIndex][columnIndex].isEmpty())
                        return false;
                    columnIndex++;
                }

            }
        } else if (startLineIndex + startColumnIndex == endLineIndex + endColumnIndex) {
            if (startLineIndex > endLineIndex) {
                int columnIndex = endColumnIndex - 1;
                for (int lineIndex = endLineIndex + 1; lineIndex < startLineIndex; lineIndex++) {
                    if (!board[lineIndex][columnIndex].isEmpty())
                        return false;
                    columnIndex--;
                }
            } else {
                int columnIndex = endColumnIndex + 1;
                for (int lineIndex = endLineIndex - 1; lineIndex > startLineIndex; lineIndex--) {
                    if (!board[lineIndex][columnIndex].isEmpty())
                        return false;
                    columnIndex++;
                }
            }
        }

        return true;
    }

    public boolean isReacheableRook(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        if (startLineIndex == endLineIndex) {
            if (startColumnIndex < endColumnIndex) {
                for (int columnIndex = startColumnIndex + 1; columnIndex < endColumnIndex; columnIndex++)
                    if (!board[startLineIndex][columnIndex].isEmpty())
                        return false;
            } else
                for (int columnIndex = startColumnIndex - 1; columnIndex > endColumnIndex; columnIndex--)
                    if (!board[startLineIndex][columnIndex].isEmpty())
                        return false;

        }
        if (startColumnIndex == endColumnIndex) {
            if (startLineIndex > endLineIndex) {
                for (int lineIndex = startLineIndex - 1; lineIndex > endLineIndex; lineIndex--)
                    if (!board[lineIndex][startColumnIndex].isEmpty())
                        return false;
            } else {
                for (int lineIndex = startLineIndex + 1; lineIndex < endLineIndex; lineIndex++)
                    if (!board[lineIndex][startColumnIndex].isEmpty())
                        return false;
            }

        }

        return true;
    }

    public boolean isReacheableQueen(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        return isReacheableRook(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex) && isReacheableBishop(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
    }

}

