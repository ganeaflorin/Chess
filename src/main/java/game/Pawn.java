package game;

public class Pawn extends Piece {
    private final String whitePath = "C:/Users/andre/IdeaProjects/chess/images/whitepawn.png";
    private final String blackPath = "C:/Users/andre/IdeaProjects/chess/images/blackpawn.png";

    public Pawn(boolean isWhite) {
        super(isWhite);
    }


    public boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking) {
        if (isAttacking) {
            if (isWhite && (startColumnIndex == endColumnIndex - 1 || startColumnIndex == endColumnIndex + 1) && startLineIndex == endLineIndex + 1) {
                return true;
            }
            if (!isWhite && (startColumnIndex == endColumnIndex - 1 || startColumnIndex == endColumnIndex + 1) && startLineIndex == endLineIndex - 1)
                return true;
            return false;
        }
        //daca nu ataca
        if (isWhite && startColumnIndex == endColumnIndex && (startLineIndex == endLineIndex + 1 || (!wasMoved && startLineIndex == endLineIndex + 2))) {
            return true;
        }
        if (!isWhite && startColumnIndex == endColumnIndex && (startLineIndex == endLineIndex - 1 || (!wasMoved && startLineIndex == endLineIndex - 2))) {
            return true;
        }
        return false;

    }


    public String getPath() {
        if (isWhite)
            return whitePath;
        return blackPath;
    }

    @Override
    public String toString() {
        return "Pawn";
    }
}
