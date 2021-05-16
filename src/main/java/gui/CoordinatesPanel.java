package gui;

import javax.swing.*;
import java.awt.*;

import static gui.SquarePanel.dark;

public class CoordinatesPanel extends JPanel {
    private JPanel letters;
    private JPanel digits;
    private GridBagConstraints gbc = new GridBagConstraints();

    public CoordinatesPanel() {
        setLetters();
        setDigits();
    }

    public JPanel getLetters() {
        return letters;

    }

    public void setLetters() {
        letters = new JPanel(new GridLayout(1, 8));
        letters.setPreferredSize(new Dimension(800, 20));
        for (int index = 1; index < 9; index++) {
            JPanel tempLetter = new JPanel(new GridBagLayout());
            tempLetter.add(new JLabel(Character.toString((char) (64 + index))));
            tempLetter.setBackground(dark);
            letters.add(tempLetter);
        }
        letters.setBackground(dark);
    }


    public void setDigits() {
        digits = new JPanel(new GridLayout(8, 1));
        for (int index = 1; index < 9; index++) {
            JPanel tempDigit = new JPanel(new GridBagLayout());
            tempDigit.add(new JLabel(String.valueOf(index)));
            tempDigit.setBackground(dark);
            digits.add(tempDigit);
        }
        digits.setPreferredSize(new Dimension(20, 800));
        digits.setBackground(dark);
    }

    public JPanel getDigits() {
        return digits;
    }
}
