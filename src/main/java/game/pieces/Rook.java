package game.pieces;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        super(isWhite);
    }

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        return startColumnIndex == endColumnIndex || startLineIndex == endLineIndex;
    }

    @Override
    public String getPath() {
        String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whiterook.png";
        if (isWhite)
            return whitePath;
        return "C:/Users/andre/IdeaProjects/chess/images/blackrook.png";
    }

    @Override
    public Rook getNewPieceOfType() {
        return new Rook(isWhite);
    }

    @Override
    public String toString() {
        return "Rook";
    }
}
