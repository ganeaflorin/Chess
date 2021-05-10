package game;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }

    private final String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whiteknight.png";
    private final String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackknight.png";

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return endLineIndex == startLineIndex - 2 && endColumnIndex == startColumnIndex + 1 ||
                endLineIndex == startLineIndex - 1 && endColumnIndex == startColumnIndex + 2 ||
                endLineIndex == startLineIndex + 1 && endColumnIndex == startColumnIndex + 2 ||
                endLineIndex == startLineIndex + 2 && endColumnIndex == startColumnIndex + 1 ||
                endLineIndex == startLineIndex + 2 && endColumnIndex == startColumnIndex - 1 ||
                endLineIndex == startLineIndex - 1 && endColumnIndex == startColumnIndex - 2 ||
                endLineIndex == startLineIndex - 2 && endColumnIndex == startColumnIndex - 1 ||
                endLineIndex == startLineIndex + 1 && endColumnIndex == startColumnIndex - 2;
    }

    @Override
    public String getPath() {
        if (isWhite)
            return whitePath;
        return blackPath;
    }

    @Override
    public String toString() {
        return "Knight";
    }
}
