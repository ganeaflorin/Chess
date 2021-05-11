package gui;

import game.Board;
import game.Move;
import game.Piece;
import game.Square;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquarePanel extends JPanel {
    private int line;
    private int column;
    public static final Color dark = new Color(112, 102, 119);
    public static final Color light = new Color(204, 183, 174);
    private final Dimension dimension = new Dimension(100, 100);

    public SquarePanel(int line, int column) {
        super(new GridBagLayout());
        this.line = line;
        this.column = column;
        setPreferredSize(dimension);
        setColor();
    }

    public void setImage(Board board) {
        this.removeAll();
        try {
            Piece piece = board.getBoard()[line][column].getPiece();
            if (piece != null) {
                String path = piece.getPath();
                BufferedImage image = ImageIO.read(new File(path));
                add(new JLabel(new ImageIcon(image)));
//                repaint();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        repaint();
        validate();
    }

    public void setColor() {
        if ((line + column) % 2 == 0)
            this.setBackground(light);
        else
            this.setBackground(dark);
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
