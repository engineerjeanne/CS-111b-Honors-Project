package gg.jeanne;

public class Letter {
    private final boolean[][] dots;
    private final char letter;

    public Letter(int[][] charMap, char letter) {
        /* Example, this would make an A
            [[0, 0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 1, 0, 0, 0, 0],
            [0, 0, 0, 0, 1, 0, 0, 0, 0],
            [0, 0, 0, 1, 0, 1, 0, 0, 0],
            [0, 0, 0, 1, 0, 1, 0, 0, 0],
            [0, 0, 1, 0, 0, 0, 1, 0, 0],
            [0, 0, 1, 1, 1, 1, 1, 0, 0],
            [0, 1, 0, 0, 0, 0, 0, 1, 0],
            [0, 1, 0, 0, 0, 0, 0, 1, 0],
            [0, 0, 0, 0, 0, 0, 0, 0, 0]]
         */

        this.letter = letter;
        this.dots = new boolean[charMap.length][charMap[0].length];

        for(int i = 0; i < charMap.length; i++) {
            for(int j = 0; j < charMap[i].length; j++) {
                this.dots[i][j] = charMap[i][j] == 1;
            }
        }
    }

    public boolean[][] getDots() {
        return this.dots;
    }

    public char getLetter() {
        return this.letter;
    }

}
