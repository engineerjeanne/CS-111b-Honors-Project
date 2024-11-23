package gg.jeanne.HttpCalls;

import java.util.Arrays;

public class Prediction {

    private final String line;
    private final String destination;
    private final int[] minutes;
    private final int carriages;

    public Prediction(String line, String dest, int carriages) {
        this.line = line;
        this.destination = dest;
        this.carriages = carriages;
        this.minutes = new int[]{0, 0};
    }

    public void addMinutes(int min) {
        if(this.minutes[0] == 0) this.minutes[0] = min;
        else this.minutes[1] = min;

    }

    public String getLine() {
        return this.line;
    }

    public String getDestination() {
        return this.destination;
    }

    public int getPredictions(int slot) {
        return this.minutes[slot - 1];
    }

    public int getCarriages() {
        return this.carriages;
    }

    @Override
    public String toString() {
        return "Prediction{" +
                "line='" + line + '\'' +
                ", destination='" + destination + '\'' +
                ", minutes=" + Arrays.toString(minutes) +
                ", carriages=" + carriages +
                '}';
    }
}
