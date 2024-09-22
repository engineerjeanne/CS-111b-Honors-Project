package HonorsProj;

import java.util.HashMap;

public class LetterMap {

    private final HashMap<Character, Letter> letterMap;

    public LetterMap() {
        this.letterMap = new HashMap<Character, Letter>();

        initialize();
    }

    public boolean[][] getDotsForChar(char c) {
        return letterMap.get(c).getDots();
    }

    public void initialize() {
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        letterMap.put('A', new Letter(matrix, 'A'));
    }
}
