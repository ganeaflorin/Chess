package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static gui.SquarePanel.dark;

public class BoardPanel extends JPanel {
    private final List<SquarePanel> boardPanels = new ArrayList<>();
    private final Dimension dimension = new Dimension(800, 800);

    public BoardPanel() {
        super(new GridLayout(8, 8));
        for (int line = 0; line < 8; line++)
            for (int column = 0; column < 8; column++) {
                SquarePanel square = new SquarePanel(line, column);
                boardPanels.add(square);
                add(square);
            }
        setPreferredSize(dimension);
        setBackground(dark);
    }

    public List<SquarePanel> getBoardPanels() {
        return boardPanels;
    }
}
