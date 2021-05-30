package game.pieces;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        if (isAttacking) {
            if (isWhite && (startColumnIndex == endColumnIndex - 1 || startColumnIndex == endColumnIndex + 1) && startLineIndex == endLineIndex + 1) {
                return true;
            }
            return !isWhite && (startColumnIndex == endColumnIndex - 1 || startColumnIndex == endColumnIndex + 1) && startLineIndex == endLineIndex - 1;
        }
        if (isWhite && startColumnIndex == endColumnIndex && (startLineIndex == endLineIndex + 1 || (!wasMoved && startLineIndex == endLineIndex + 2))) {
            return true;
        }
        return !isWhite && startColumnIndex == endColumnIndex && (startLineIndex == endLineIndex - 1 || (!wasMoved && startLineIndex == endLineIndex - 2));
    }

    public String getPath() {
        String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whitepawn.png";
        if (isWhite)
            return whitePath;
        return "C:/Users/andre/IdeaProjects/chess/images/blackpawn.png";
    }

    @Override
    public String toString() {
        return "Pawn";
    }

    @Override
    public Pawn getNewPieceOfType() {
        return new Pawn(isWhite);
    }
}
