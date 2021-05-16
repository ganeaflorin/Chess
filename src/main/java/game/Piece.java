package game;

public abstract class Piece {
    protected boolean isWhite;
    protected boolean wasMoved = false;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;

    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean isValidMove(int startLineIndex, int startColumnIndex, int endLineIndex, int endColumnIndex, boolean isAttacking);

    public abstract String getPath();

    public boolean isWasMoved() {
        return wasMoved;
    }

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }

    public abstract Piece getNewPieceOfType();
}

