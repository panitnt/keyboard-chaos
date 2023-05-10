import javax.swing.*;

public class Game {
    private WordController word;
    private Setting setting;
    private Stopwatch stopwatch;
    private GameUI gameUI;

    public Game(){
//        word = new WordController();
        setting = Setting.getInstance();
        stopwatch = Stopwatch.getInstance();
    }
    public void starts() {
        stopwatch.start();
        // start game logic
    }

    public void stops() {
        stopwatch.stop();
        // stop game logic
    }
    public double getTime() {
        return stopwatch.getElapsedTime();
    }

    public void setGame() {
        setting.setMode("word");
        setting.setLevel(1);
    }

//    public static void main(String[] args) {
//        Game game = new Game();
//        game.starts();
//    }
}
