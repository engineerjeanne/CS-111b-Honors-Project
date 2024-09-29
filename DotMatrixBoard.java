package HonorsProj;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class DotMatrixBoard extends JPanel {
    private final int rows;
    private final int cols;
    private final boolean[][] dotMatrix;
    private final LetterMap letterMap;
    private int yOffset;

    public DotMatrixBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.dotMatrix = new boolean[rows][cols];
        this.letterMap = new LetterMap();
        this.yOffset = 0;

        setSize(rows*8, cols*8);
        setBackground(Color.getHSBColor(3, 0.667f, 0.129f));

        clearBoard();

        setText("<hr2/>");
        setText("SF AIRPORT<split/>15, 23 MIN");
        setText("8-CAR, YL LINE");
        setText("<hr4/>");
        setText("SFO/MILLBRAE<split/>18, 28 MIN");
        setText("6-CAR, RD LINE");
    }

    public void setText(String text) {
        int xOffset = 0;
        int addYOffset = 0;

        text = text.replace("<split/>", "¤");
        String[] splitText = text.split("¤");
        String leftText = splitText[0];
        String rightText = null;

        if(splitText.length > 1) rightText = splitText[1];

        if(Objects.equals(text, "<hr2/>")) {
            addYOffset += 2;
        } else if(Objects.equals(text, "<hr4/>")) {
            addYOffset += 4;
        } else if(Objects.equals(text, "<hr6/>")) {
            addYOffset += 6;
        } else {
            for (int i = 0; i < leftText.length(); i++) {
                boolean[][] charmap = letterMap.getDotsForChar(leftText.charAt(i));

                for (int j = 0; j < charmap.length; j++) {
                    for (int k = 0; k < charmap[j].length; k++) {
                        setDot(j + yOffset, k + xOffset, charmap[j][k]);

                        if (j > addYOffset) addYOffset = j + 1;
                    }
                }

                xOffset += charmap[0].length;
            }

            if (Objects.nonNull(rightText)) {
                int totalWidth = 0;

                // Calculate total width of the text
                for (int i = 0; i < rightText.length(); i++) {
                    boolean[][] charmap = letterMap.getDotsForChar(rightText.charAt(i));
                    totalWidth += charmap[0].length;
                }

                // Set xOffset for right alignment
                xOffset = this.cols - totalWidth;

                for (int i = 0; i < rightText.length(); i++) {
                    boolean[][] charmap = letterMap.getDotsForChar(rightText.charAt(i));

                    for (int j = 0; j < charmap.length; j++) {
                        for (int k = 0; k < charmap[j].length; k++) {
                            setDot(j + yOffset, k + xOffset, charmap[j][k]);

                            if (j > addYOffset) addYOffset = j + 1;
                        }
                    }

                    xOffset += charmap[0].length;
                }
            }
        }

        this.yOffset += addYOffset;
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