package game;

public class Square {
    private Piece piece;
    private boolean isEmpty = true;
    private int x;
    private int y;

    public Square(int x, int y, Piece piece) {
        this.piece = piece;
        if (piece != null) isEmpty = false;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        isEmpty = false;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty() {
        piece = null;
        isEmpty = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordonates(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
