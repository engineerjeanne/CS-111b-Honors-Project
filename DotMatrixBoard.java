package HonorsProj;

import javax.swing.*;
import java.awt.*;

public class DotMatrixBoard extends JPanel {
    private final int rows;
    private final int cols;
    private final boolean[][] dotMatrix;
    private final LetterMap letterMap;

    public DotMatrixBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.dotMatrix = new boolean[rows][cols];
        this.letterMap = new LetterMap();

        setSize(rows*8, cols*8);
        setBackground(Color.getHSBColor(3, 0.667f, 0.129f));

        clearBoard();

        setText("A");
    }

    public void setText(String text) {
        for(int i = 0; i < text.length(); i++) {
            boolean[][] charmap = letterMap.getDotsForChar(text.charAt(i));

            for(int j = 0; j < charmap.length; j++) {
                for(int k = 0; k < charmap[j].length; k++) {
                    setDot(j, k, charmap[j][k]);
                }
            }
        }
    }

    // Method to clear the dot matrix (turn all dots off)
    private void clearBoard() {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                dotMatrix[row][col] = false;
            }
        }

        // We use repaint() anywhere when we update the board
        repaint();
    }

    // Method to set a specific dot on the matrix
    private void setDot(int row, int col, boolean on) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            dotMatrix[row][col] = on;

            repaint();
        }
    }

    // Override paintComponent so the JPanel uses our own paintComponentFunction that paints the dot matrix displays
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        int dotSize = 8;
        int xOffset = (getWidth() - cols * dotSize) / 2;
        int yOffset = (getHeight() - rows * dotSize) / 2;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (dotMatrix[row][col]) {
                    graphic.setColor(Color.RED);
                } else {
                    graphic.setColor(Color.DARK_GRAY);
                }
                graphic.fillOval(xOffset + col * dotSize, yOffset + row * dotSize, dotSize - 1, dotSize - 1);
            }
        }
    }
}