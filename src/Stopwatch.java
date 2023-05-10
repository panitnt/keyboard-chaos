import java.lang.*;

public class Stopwatch {
    private long startTime = 0;
    private long stopTime = 0;
    private boolean isRunning = false;
    private final long nanoSecondsPerSecond = 1000000000;
    private static Stopwatch instance = null;
    public static Stopwatch getInstance() {
        if (instance == null) {
            instance = new Stopwatch();
        }
        return instance;
    }

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
            elapsedTime = (System.nanoTime() - this.stopTime);
        else
            elapsedTime = (this.stopTime - this.startTime);

        return elapsedTime / nanoSecondsPerSecond;
    }
}
