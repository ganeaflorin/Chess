package game;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    private final String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whiterook.png";
    private final String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackrook.png";

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return startColumnIndex == endColumnIndex || startLineIndex == endLineIndex;
    }

    @Override
    public String getPath() {
        if (isWhite)
            return whitePath;
        return blackPath;
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
