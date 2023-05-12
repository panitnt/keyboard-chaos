import java.lang.*;

public class Stopwatch {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean isRunning = false;
    private final long nanoSecondsPerSecond = 1000000000;
    private final long nanoSecondsPerMilliSecond = 1000000;
    private static Stopwatch instance = null;

    public void start() {
        this.startTime = System.nanoTime();
        this.isRunning = true;
    }

    public void stop() {
        this.stopTime = System.nanoTime();
        this.isRunning = false;
    }

    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.isRunning = false;
    }

    public double getElapsedTime() {

        long elapsedTime;

        if (isRunning)
            elapsedTime = (System.nanoTime() - this.startTime);
        else
            elapsedTime = (this.stopTime - this.startTime);

        return elapsedTime / nanoSecondsPerSecond;
    }

    public double getElapsedTimeMilliSecond() {
        long elapsedTime;

        if (isRunning)
            elapsedTime = (System.nanoTime() - this.startTime);
        else
            elapsedTime = (this.stopTime - this.startTime);

        return elapsedTime / nanoSecondsPerMilliSecond;
    }
}
