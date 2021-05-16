package game;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return startLineIndex + startColumnIndex == endLineIndex + endColumnIndex || startLineIndex - startColumnIndex == endLineIndex - endColumnIndex;
    }

    @Override
    public String getPath() {
        String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whitebishop.png";
        if (isWhite)
            return whitePath;
        String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackbishop.png";
        return blackPath;
    }

    @Override
    public Bishop getNewPieceOfType() {
        return new Bishop(isWhite);
    }

    @Override
    public String toString() {
        return "Bishop";
    }
}
