package game;

public class Board {
    private Square[][] board = new Square[8][8];

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


    public void printBoard() {
        for (int lineIndex = 0; lineIndex < 8; lineIndex++) {
            for (int columnIndex = 0; columnIndex < 8; columnIndex++)
                if (!board[lineIndex][columnIndex].isEmpty())
                    System.out.print(board[lineIndex][columnIndex].getPiece().toString() + " ");
                else System.out.print("------ ");
            System.out.println();
        }
    }


    public Square[][] getBoard() {
        return board;
    }
}
