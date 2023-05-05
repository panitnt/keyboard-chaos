import java.lang.*;

public class Stopwatch {
    private final long time = 0;

    public double start() {
        long begin = System.currentTimeMillis();
        return begin;
    }

    public double elapsedTime() {
        double start = this.start();
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

    public static void main(String[] args) {

    }
}
