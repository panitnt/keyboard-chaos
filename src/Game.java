import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Game {
    private WordController wordController;
    private Setting setting;
    private Stopwatch stopwatch;
    private Thread thread;
    public int index = 0;
    protected List<Character> word_generate = new ArrayList<Character>();

    public Game() {
        setting = Setting.getInstance();
        stopwatch = Stopwatch.getInstance();
        initWordController();
    }

    public void initWordController() {
        List<java.lang.Character> charList = new ArrayList<>();
        charList.add('H');
        charList.add('E');
        charList.add('L');
        charList.add('L');
        charList.add('O');
        charList.add(' ');
        charList.add('W');
        charList.add('O');
        charList.add('R');
        charList.add('L');
        charList.add('D');
        charList.add(' ');
        charList.add('Y');
        charList.add('A');
        charList.add('Y');


        for (java.lang.Character c : charList) {
            word_generate.add(new Character(c));
        }
    }

    public void start() {
        // start typing == start game

        stopwatch.start();
        setting.setMode("easy");
        setting.setLevel(1);
        // start game logic

//        thread = new Thread() {
//            @Override
//            public void run() {
//                while (true){
//                    notifyObservers();
//                }
//            }
//        };
//        thread.start();
    }

    public void stops() {
        stopwatch.stop();
        // stop game logic
    }

    public void resets() {
        stopwatch.reset();
        // reset game logic
        // reset everything word, time, wpm,
    }

    public int wordPerMinuteCalculation() {
        // calculate word per minutes
        return 0;
    }

    public double getTime() {
        return stopwatch.getElapsedTime();
    }

    public void setGame() {
        setting.setMode("word");
        setting.setLevel(1);
    }
}