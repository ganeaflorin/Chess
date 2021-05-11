package game;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    private final String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whitequeen.png";
    private final String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackqueen.png";

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return (startLineIndex + startColumnIndex == endLineIndex + endColumnIndex || startLineIndex - startColumnIndex == endLineIndex - endColumnIndex) ||
                (startColumnIndex == endColumnIndex || startLineIndex == endLineIndex);
    }

    @Override
    public String getPath() {
        if (isWhite)
            return whitePath;
        return blackPath;
    }

    @Override
    public Queen getNewPieceOfType() {
        return new Queen(isWhite);
    }

    @Override
    public String toString() {
        return "Queen";
    }
}
