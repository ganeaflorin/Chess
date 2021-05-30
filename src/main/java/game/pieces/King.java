package game.pieces;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return endLineIndex == startLineIndex && endColumnIndex == startColumnIndex - 1 ||
                endLineIndex == startLineIndex - 1 && endColumnIndex == startColumnIndex - 1 ||
                endLineIndex == startLineIndex - 1 && endColumnIndex == startColumnIndex ||
                endLineIndex == startLineIndex - 1 && endColumnIndex == startColumnIndex + 1 ||
                endLineIndex == startLineIndex && endColumnIndex == startColumnIndex + 1 ||
                endLineIndex == startLineIndex + 1 && endColumnIndex == startColumnIndex + 1 ||
                endLineIndex == startLineIndex + 1 && endColumnIndex == startColumnIndex ||
                endLineIndex == startLineIndex + 1 && endColumnIndex == startColumnIndex - 1 ||
                (!wasMoved && endLineIndex == startLineIndex && (endColumnIndex == startColumnIndex - 4 || endColumnIndex == startColumnIndex + 3));
    }

    @Override
    public String getPath() {
        String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whiteking.png";
        if (isWhite)
            return whitePath;
        return "C:/Users/andre/IdeaProjects/chess/images/blackking.png";
    }

    @Override
    public King getNewPieceOfType() {
        return new King(isWhite);
    }

    @Override
    public String toString() {
        return "King";
    }

}
