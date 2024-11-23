package gg.jeanne;

import javax.swing.*;
import java.awt.*;

public class GUI {
    private final DotMatrixBoard dotMatrixBoard;

    /**
     * This will be modified to make the Main class be able to communicate with the DotMatrixBoard.
     */
    public GUI() {
        JFrame frame = new JFrame();

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setLayout(new GridLayout(0, 1));

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BART Predictions LEDs");
        frame.pack();
        frame.setVisible(true);
        frame.setSize(150*8 + 10, 51*8 + 10);

        this.dotMatrixBoard = new DotMatrixBoard(50, 150);

        panel.add(dotMatrixBoard);
    }

    public DotMatrixBoard getDotMatrixBoard() {
        return this.dotMatrixBoard;
    }


}
