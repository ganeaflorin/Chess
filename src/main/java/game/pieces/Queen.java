package game.pieces;

public class Queen extends Piece {
    public Queen(boolean isWhite) {
        super(isWhite);
    }

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return (startLineIndex + startColumnIndex == endLineIndex + endColumnIndex || startLineIndex - startColumnIndex == endLineIndex - endColumnIndex) ||
                (startColumnIndex == endColumnIndex || startLineIndex == endLineIndex);
    }

    @Override
    public String getPath() {
        String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whitequeen.png";
        if (isWhite)
            return whitePath;
        return "C:/Users/andre/IdeaProjects/chess/images/blackqueen.png";
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
