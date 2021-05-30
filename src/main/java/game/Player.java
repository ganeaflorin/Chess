package game;

public class Player {
    private String name;
    private double score;
    private boolean isWhite = false;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public void setWhite(boolean white) {
        isWhite = white;
    }

    public void incrementScore() {
        score += 1;
    }
}
