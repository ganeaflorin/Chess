package game;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final Square[][] board;
    private boolean isWhiteChecked = false;
    private boolean isBlackChecked = false;

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
        if (!isReachable(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex))
            return false;
        return true;
    }

    //    private boolean simulateMoveForCheck(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
//        Square startSquare = board[startLineIndex][startColumnIndex];
//        Square endSquare = board[endLineIndex][endColumnIndex];
//        Square auxEndSquare = null;
//        if (!endSquare.isEmpty())
//            auxEndSquare = new Square(endSquare.getX(), endSquare.getY(), endSquare.getPiece().getNewPieceOfType());
//        endSquare.setPiece(startSquare.getPiece());
//        startSquare.setEmpty();
//        boolean isChecked = (getCheckedSquare() != null);
//        startSquare.setPiece(endSquare.getPiece());
//        if (auxEndSquare.isEmpty())
//            endSquare.setEmpty();
//        else
//            endSquare.setPiece(auxEndSquare.getPiece());
//        return isChecked;
//    }
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
                            System.out.println("CHECKED!!!");
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
                if (!board[lineIndex][columnIndex].isEmpty() &&
                        board[lineIndex][columnIndex].getPiece().toString().equals("King")) {
                    if (firstKingSq == null)
                        firstKingSq = board[lineIndex][columnIndex];
                    if (firstKingSq != null) secondKingSq = board[lineIndex][columnIndex];
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
        if (isCheck() && countAvailableMoves == 0)
            System.out.println("CHECKMATE!!!!");
        return isCheck() && countAvailableMoves == 0;
    }

//    public boolean isStalemate(int turn) {
//        int countPieces = 0;
//        for (int lineIndex = 0; lineIndex < 8; lineIndex++)
//            for (int columnIndex = 0; columnIndex < 8; columnIndex++)
//                if (!board[lineIndex][columnIndex].isEmpty())
//                    countPieces++;
//        if () //
//            return countPieces == 2 || (!isCheck() &&);
//    }

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
                        if ((isBlackChecked && !isWhite) || (isWhiteChecked && isWhite))
                            availableSquares.add(candidateSquare);
                    }

                    revertTempMove(square, candidateSquare, auxEnd);

                }
            }
        return availableSquares;
    }

    public boolean makeMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];
        Square endSquare = board[endLineIndex][endColumnIndex];
//        Square auxEndSquare = null;
//        if (!endSquare.isEmpty())
//            auxEndSquare = new Square(endSquare.getX(), endSquare.getY(), endSquare.getPiece().getNewPieceOfType());
//        Square checkedSquare;
//        if (validateMove(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex)) {
        if (availableMoves(startSquare).contains(endSquare)) {
            endSquare.setPiece(startSquare.getPiece());
            startSquare.setEmpty();
//            if ((checkedSquare = getCheckedSquare()) != null && checkedSquare.getPiece().isWhite() == endSquare.getPiece().isWhite()) {
//                startSquare.setPiece(endSquare.getPiece());
//                if (auxEndSquare == null)
//                    endSquare.setEmpty();
//                else
//                    endSquare.setPiece(auxEndSquare.getPiece());
//                return false;
//            }
            endSquare.getPiece().setWasMoved(true);
            return true;
        }
        return false;
    }
    //TODO: BUG: DACA EU SUNT ALB SI TREBUIE SA MUT, DACA DAU CLICK PE O PIESA NEAGRA TREBUIE DUPA SA DAU 2 CLICKURI CA SA MUT
    //TODO: STALEMATE
    //TODO: PAWN PROMOTION
    //TODO: CASTLE

    public boolean isReachable(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex) {
        Square startSquare = board[startLineIndex][startColumnIndex];

        switch (startSquare.getPiece().toString()) {
            case "Rook":
                return isReachableRook(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Queen":
                return isReachableQueen(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Bishop":
                return isReachableBishop(startLineIndex, startColumnIndex, endLineIndex, endColumnIndex);
            case "Pawn":
                return isReachablePawn(startLineIndex, startColumnIndex, endLineIndex);
            default:
                return true;
        }
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

}

