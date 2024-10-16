package gg.jeanne;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class DotMatrixBoard extends JPanel {
    private final int rows;
    private final int cols;
    private final boolean[][] dotMatrix;
    private final LetterMap letterMap;
    private final ArrayList<ArrayList<String>> queue;
    private int yOffset;
    private int scrollOffset;

    /**
     * The main workhorse of my program.
     * This manages the queue, and the displays.
     * @param rows: How wide (in px) the board should be.
     * @param cols: How tall (in px) the board should be.
     */
    public DotMatrixBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.dotMatrix = new boolean[rows][cols * 2];
        this.letterMap = new LetterMap();
        this.yOffset = 0;
        this.scrollOffset = 0;
        this.queue = new ArrayList<>();

        setSize(rows * 8, cols);
        setBackground(Color.getHSBColor(3, 0.667f, 0.129f));

        // Test data
        ArrayList<String> test = new ArrayList<>();
        test.add("SFO/MILLBRAE<split/> 3, 23 MIN");
        test.add("6-CAR, RD-LINE");
        test.add("<hr6/>");
        test.add("SF AIRPORT<split/> 7, 15 MIN");
        test.add("8-CAR, YL-LINE");
        queue.add(test);

        ArrayList<String> test2 = new ArrayList<>();
        test2.add("<hr6/>");
        test2.add("WELCOME TO BART.");
        test2.add("THANKS FOR RIDING BART.");
        test2.add("<hr6/>");
        test2.add("<hr6/>");
        test2.add("JEANNE IS THE BEST CODER.");
        queue.add(test2);

        // Start the queue handler
        SwingUtilities.invokeLater(this::handleQueueRecursively);
    }

    public void handleQueueRecursively() {
        if (this.queue.isEmpty()) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ignored) { }

            handleQueueRecursively();
        }

        // Safely retrieve the first ArrayList<String> from the queue
        ArrayList<String> inQueue = this.queue.remove(0);

        clearBoard();

        // Process the current ArrayList<String> entirely before moving on to the next one
        processMessages(inQueue, () -> {
            // After finishing the current array, scroll and then move to the next array in the queue
            startAutoScroll(150, inQueue.size() * 10, this::handleQueueRecursively);
        });
    }

    // Sequentially display all messages from the given ArrayList
    private void processMessages(ArrayList<String> messages, Runnable onComplete) {
        if (messages.isEmpty()) {
            onComplete.run();  // Move to the next queue element if the list is empty
            return;
        }

        // Process and display all messages in the current list
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (String message : messages) {
                    setText(message);
                }
                return null;
            }

            @Override
            protected void done() {
                onComplete.run();  // Call the completion callback after displaying all messages
            }
        };

        worker.execute();
    }

    // This was supposed to scroll on it's on but I decided against it eventually
    // Now it's a helper function for the other one below.
    private void scroll() {
        // Increment scrollOffset by moving up
        scrollOffset++;
        if (scrollOffset >= rows) {
            scrollOffset = 0; // Reset scrollOffset when reaching the bottom
        }

        // Shift the dots downwards by 1 row
        for (int row = 0; row < rows - 1; row++) {
            dotMatrix[row] = dotMatrix[row + 1].clone();  // Shift row data downwards
        }

        // Clear the last row to simulate scrolling upwards
        for (int col = 0; col < cols; col++) {
            dotMatrix[rows - 1][col] = false;  // Clear the last row
        }

        repaint();  // Repaint the matrix after shifting
    }


    // Make it auto scroll for the scrolling animation that the screens have in irl
    // This is also blocking so we can sequentially implement it in the code
    public void startAutoScroll(int delay, int iterations, Runnable onComplete) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Start from the bottom of the matrix
                scrollOffset = rows - 1;

                for (int i = 0; i < iterations; i++) {
                    // Scroll the text
                    scroll();

                    // Pause the background task
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interrupt status
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                // Run the onComplete callback once scrolling is done
                if (onComplete != null) {
                    onComplete.run();
                }
            }
        };

        worker.execute();
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
        this.yOffset = 0;

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