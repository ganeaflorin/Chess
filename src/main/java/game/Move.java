package game;

import game.pieces.Piece;
import game.pieces.Queen;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final Square[][] board;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;
    private Piece lastCapturedPiece;

    public Move(Board board) {
        this.board = board.getBoard();
    }

    public boolean validateMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
        boolean isAttacking = false;
        if (!endSquare.isEmpty())
            isAttacking = true;
        if (startSquare.isEmpty()) return false;
        if (startSquare.getPiece().toString().equals("King") && ((endColumnIndex == startColumnIndex - 4 || endColumnIndex == startColumnIndex + 3))) {
            //do nothing

        } else if (!endSquare.isEmpty() && startSquare.getPiece().isWhite() == endSquare.getPiece().isWhite())
            return false;
        if (!startSquare.getPiece().isValidMove(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex, isAttacking))
            return false;
        return isReachable(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
    }

    private void makeTempMove(Square start, Square end) {
        end.setPiece(start.getPiece());
        start.setEmpty();
    }

    private void revertTempMove(Square start, Square end, Square auxEnd) {
        start.setPiece(end.getPiece());
        if (auxEnd == null)
            end.setEmpty();
        else
            end.setPiece(auxEnd.getPiece());
    }

    public Square getCheckedSquare() {
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                if (!board[lineIndex][columnIndex].isEmpty()) {
                    List<Square> availableMoves = availableMoves(board[lineIndex][columnIndex]);
                    for (Square square : availableMoves)
                        if (!square.isEmpty() && square.getPiece().toString().equals("King")) {
                            return square;
                        }
                }
            }
        return null;
    }

    public boolean isCheck() {
        Square firstKingSq = null;
        Square secondKingSq = null;
        isWhiteChecked = false;
        isBlackChecked = false;
        boolean isCheck = false;
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                Square currentSquare = board[lineIndex][columnIndex];
                if (!currentSquare.isEmpty() &&
                        currentSquare.getPiece().toString().equals("King")) {
                    if (firstKingSq == null)
                        firstKingSq = currentSquare;
                    if (firstKingSq != null) secondKingSq = currentSquare;
                }
            }
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                Square currentSquare = board[lineIndex][columnIndex];
                if (!currentSquare.isEmpty()) {
                    if (currentSquare.getPiece().isWhite() != firstKingSq.getPiece().isWhite() &&
                            validateMove(currentSquare.getX(), currentSquare.getY(), firstKingSq.getX(), firstKingSq.getY())) {
                        isCheck = true;
                        if (currentSquare.getPiece().isWhite())
                            isWhiteChecked = true;
                        else isBlackChecked = true;
                    } else if (currentSquare.getPiece().isWhite() != secondKingSq.getPiece().isWhite() &&
                            validateMove(currentSquare.getX(), currentSquare.getY(), secondKingSq.getX(), secondKingSq.getY())) {
                        isCheck = true;
                        if (currentSquare.getPiece().isWhite())
                            isWhiteChecked = true;
                        else isBlackChecked = true;
                    }
                }
            }
        return isCheck;
    }

    public boolean isCheckMate() {
        int countAvailableMoves = 0;
        if (isCheck()) {
            Square checkedSquare = getCheckedSquare();
            if (checkedSquare.getPiece().isWhite()) {
                for (int lineIndex = 0; lineIndex < 8; lineIndex++)
                    for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                        Square square = board[lineIndex][columnIndex];
                        if (!square.isEmpty() && square.getPiece().isWhite())
                            countAvailableMoves += availableMoves(square).size();
                    }
            }
            if (!checkedSquare.getPiece().isWhite()) {
                for (int lineIndex = 0; lineIndex < 8; lineIndex++)
                    for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                        Square square = board[lineIndex][columnIndex];
                        if (!square.isEmpty() && !square.getPiece().isWhite())
                            countAvailableMoves += availableMoves(square).size();
                    }
            }
        }
        return isCheck() && countAvailableMoves == 0;
    }

    public List<Square> availableMoves(Square square) {
        List<Square> availableSquares = new ArrayList<>();
        boolean isWhite = square.getPiece().isWhite();
//        Square checkedSquare = getCheckedSquare();
        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                Square candidateSquare = board[lineIndex][columnIndex];
                if (validateMove(square.getX(), square.getY(), lineIndex, columnIndex)) {
                    Square auxEnd = null;
                    if (!candidateSquare.isEmpty())
                        auxEnd = new Square(candidateSquare.getX(), candidateSquare.getY(), candidateSquare.getPiece().getNewPieceOfType());
                    makeTempMove(square, candidateSquare);
                    if (!isCheck())
                        availableSquares.add(candidateSquare);
                    else {
                        if ((isBlackChecked && !isWhite && !isWhiteChecked) || (isWhiteChecked && isWhite && !isBlackChecked))
                            availableSquares.add(candidateSquare);
                    }

                    revertTempMove(square, candidateSquare, auxEnd);

                }
            }
        return availableSquares;
    }

    public void castle(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
        if ((endColumnIndex == startColumnIndex - 4)) {
            board[endLineIndex][endColumnIndex + 2].setPiece(startSquare.getPiece());
            board[endLineIndex][endColumnIndex + 3].setPiece(endSquare.getPiece());
            startSquare.setEmpty();
            endSquare.setEmpty();
        } else if (endColumnIndex == startColumnIndex + 3) {
            board[endLineIndex][endColumnIndex - 1].setPiece(startSquare.getPiece());
            board[endLineIndex][endColumnIndex - 2].setPiece(endSquare.getPiece());
            startSquare.setEmpty();
            endSquare.setEmpty();
        }
    }

    public boolean makeMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        lastCapturedPiece = null;
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
        if (availableMoves(startSquare).contains(endSquare)) {
            if (startSquare.getPiece().toString().equals("King") && (endColumnIndex == startColumnIndex - 4 || endColumnIndex == startColumnIndex + 3)) {
                castle(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            } else {
                if (!pawnPromotion(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex)) {
                    if (!endSquare.isEmpty())
                        lastCapturedPiece = endSquare.getPiece();
                    endSquare.setPiece(startSquare.getPiece());
                    startSquare.setEmpty();
                    endSquare.getPiece().setWasMoved(true);
                }

            }
            return true;
        }
        return false;
    }

    public boolean pawnPromotion(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
        if ((endLineIndex == 0 || endLineIndex == 7) && startSquare.getPiece().toString().equals("Pawn")) {
            if (!endSquare.isEmpty()) lastCapturedPiece = endSquare.getPiece();
            endSquare.setPiece(new Queen(startSquare.getPiece().isWhite()));
            startSquare.setEmpty();
            return true;
        }
        return false;
    }

    public boolean isReachable(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        return switch (startSquare.getPiece().toString()) {
            case "Rook" -> isReachableRook(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Queen" -> isReachableQueen(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Bishop" -> isReachableBishop(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Pawn" -> isReachablePawn(startLineIndex, startColumnIndex, endLineIndex);
            case "King" -> isReachableKing(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            default -> true;
        };
    }

    private boolean isReachableKing(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square endSquare = board[endLineIndex][endColumnIndex];
        Square startSquare = board[startLineIndex][startColumnIndex];
        if (endLineIndex == startLineIndex) {
            if (endColumnIndex == startColumnIndex - 4) {
                if (endSquare.isEmpty() || !endSquare.getPiece().toString().equals("Rook") || endSquare.getPiece().isWasMoved())
                    return false;
                else {
                    for (int columnIndex = endColumnIndex + 1; columnIndex < startColumnIndex; columnIndex++) {
                        Square candidateSquare = board[startLineIndex][columnIndex];
                        if (!candidateSquare.isEmpty()) return false;
                        if (candidateSquare.isEmpty()) {
                            candidateSquare.setPiece(startSquare.getPiece());
                            startSquare.setEmpty();
                            boolean isChecked = isCheck();
                            startSquare.setPiece(candidateSquare.getPiece());
                            candidateSquare.setEmpty();
                            if (!isChecked) return false;
                        }
                    }
                }
            }

            if (endColumnIndex == startColumnIndex + 3) {
                if (endSquare.isEmpty() || !endSquare.getPiece().toString().equals("Rook") || endSquare.getPiece().isWasMoved())
                    return false;
                else {
                    for (int columnIndex = startColumnIndex + 1; columnIndex < endColumnIndex; columnIndex++) {
                        Square candidateSquare = board[startLineIndex][columnIndex];
                        if (!candidateSquare.isEmpty()) return false;
                        else {
                            candidateSquare.setPiece(startSquare.getPiece());
                            startSquare.setEmpty();
                            boolean isChecked = isCheck();
                            startSquare.setPiece(candidateSquare.getPiece());
                            candidateSquare.setEmpty();
                            if (!isChecked) return false;

                        }
                    }
                }
            }

        }
        return true;
    }

    private boolean isReachablePawn(int startLineIndex, int starColumnIndex, int endLineIndex) {
        if (startLineIndex == endLineIndex + 2 && !board[startLineIndex - 1][starColumnIndex].isEmpty())
            return false;
        return startLineIndex != endLineIndex - 2 || board[startLineIndex + 1][starColumnIndex].isEmpty();
    }

    private boolean isReachableBishop(int startLineIndex, int startColumnIndex, int endLineIndex,
                                      int endColumnIndex) {
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

    public boolean isReachableRook(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
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

    public boolean isReachableQueen(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        return isReachableRook(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex) && isReachableBishop(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
    }

    public Piece getLastCapturedPiece() {
        return lastCapturedPiece;
    }
}

