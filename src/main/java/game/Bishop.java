package game;

public class Bishop extends Piece {

    private final String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whitebishop.png";
    private final String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackbishop.png";

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return startLineIndex + startColumnIndex == endLineIndex + endColumnIndex || startLineIndex - startColumnIndex == endLineIndex - endColumnIndex;
    }

    @Override
    public String getPath() {
        if (isWhite)
            return whitePath;
        return blackPath;
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}
