import java.lang.*;

public class Stopwatch {
    private final long time = 0;
    private static Stopwatch instance = null;
    public static Stopwatch getInstance() {
        if (instance == null) {
            instance = new Stopwatch();
        }
        return instance;
    }

    public double start() {
        long timeBegin = System.currentTimeMillis();
        return timeBegin;
    }

    public double elapsedTime() {
        double start = this.start();
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public static void main(String[] args) {

    }
}
