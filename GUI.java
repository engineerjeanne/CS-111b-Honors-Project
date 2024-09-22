package HonorsProj;

import javax.swing.*;
import java.awt.*;

public class GUI {

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
        frame.setSize(200*8 + 10, 50*8 + 100);

        DotMatrixBoard dotMatrixBoard = new DotMatrixBoard(50, 200);

        panel.add(dotMatrixBoard);
    }

}
