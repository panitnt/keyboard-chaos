import javax.swing.*;

public class Game {
    private WordController word;
    private Setting setting;
    private Stopwatch stopwatch;

    public Game(){
//        word = new WordController();
        setting = Setting.getInstance();
        stopwatch = Stopwatch.getInstance();
    }
    public void starts() {
        stopwatch.start();
        setting.setMode("easy");
        setting.setLevel(1);
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
