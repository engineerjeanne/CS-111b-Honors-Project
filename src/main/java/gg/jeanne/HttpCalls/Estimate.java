package gg.jeanne.HttpCalls;

public class Estimate {

    private final int delay, minutes, length, platform;
    private final String color, hexcolor, direction, destination;
    private final boolean dynamic, cancelled, bikesAllowed;

    public Estimate(
            int delay, int minutes, int length, int platform,
            String color, String hexcolor, String direction, String destination,
            int dynamic, int cancelled, int bikesAllowed
    ) {
        this.delay = delay;
        this.minutes = minutes;
        this.length = length;
        this.platform = platform;
        this.color = color;
        this.destination = destination;
        this.hexcolor = hexcolor;
        this.direction = direction;
        this.dynamic = dynamic == 1;
        this.cancelled = cancelled == 1;
        this.bikesAllowed = bikesAllowed == 1;
    }

    public boolean isBikesAllowed() {
        return bikesAllowed;
    }

    public String getDestination() {
        return destination;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public int getDelay() {
        return delay;
    }

    public int getLength() {
        return length;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getPlatform() {
        return platform;
    }

    public String getColor() {
        return color;
    }

    public String getDirection() {
        return direction;
    }

    public String getHexcolor() {
        return hexcolor;
    }


}
