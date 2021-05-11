package game;

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
                endLineIndex == startLineIndex + 1 && endColumnIndex == startColumnIndex - 1;
    }

    @Override
    public String getPath() {
        String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whiteking.png";
        if (isWhite)
            return whitePath;
        String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackking.png";
        return blackPath;
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
