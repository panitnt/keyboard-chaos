import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {
    private WordController wordController;
    private Setting setting;
    protected Stopwatch stopwatch;
    private Thread thread;
    public int index = 0;
    protected List<Character> word_generate = new ArrayList<Character>();
    private boolean isPlaying = false;
    private int correctWord = 0;
    public float keyPress = 0;

    public Game() {
        setting = Setting.getInstance();
        stopwatch = Stopwatch.getInstance();
        initWordController();
    }

    public void initWordController() {
        List<java.lang.Character> charList = new ArrayList<>();
        charList.add('h');
        charList.add('e');
        charList.add('l');
        charList.add('l');
        charList.add('o');
        charList.add(' ');
        charList.add('w');
        charList.add('o');
        charList.add('r');
        charList.add('l');
        charList.add('d');
        charList.add(' ');
        charList.add('y');
        charList.add('a');
        charList.add('y');
        charList.add('h');
        charList.add('e');
        charList.add('l');
        charList.add('l');
        charList.add('o');
        charList.add(' ');
        charList.add('w');
        charList.add('o');
        charList.add('r');
        charList.add('l');
        charList.add('d');
        charList.add(' ');
        charList.add('y');
        charList.add('a');
        charList.add('y');
        charList.add('h');
        charList.add('e');
        charList.add('l');
        charList.add('l');
        charList.add('o');
        charList.add(' ');
        charList.add('w');
        charList.add('o');
        charList.add('r');
        charList.add('l');
        charList.add('d');
        charList.add(' ');
        charList.add('y');
        charList.add('a');
        charList.add('y');
        charList.add('h');
        charList.add('e');
        charList.add('l');
        charList.add('l');
        charList.add('o');
        charList.add(' ');
        charList.add('w');
        charList.add('o');
        charList.add('r');
        charList.add('l');
        charList.add('d');
        charList.add(' ');
        charList.add('y');
        charList.add('a');
        charList.add('y');
        charList.add('h');
        charList.add('e');
        charList.add('l');
        charList.add('l');
        charList.add('o');
        charList.add(' ');
        charList.add('w');
        charList.add('o');
        charList.add('r');
        charList.add('l');
        charList.add('d');
        charList.add(' ');
        charList.add('y');
        charList.add('a');
        charList.add('y');


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

        thread = new Thread(() -> {
            while (true) {
                setChanged();
                notifyObservers();
            }
        });
        thread.start();
    }

    public void stops() {
        stopwatch.stop();
        // stop game logic
    }

    public void resets() {
        stopwatch.reset();
        // reset game logic
        // reset everything word, time, wpm,
        word_generate.clear();
        index = 0;
        initWordController();
        keyPress = 0;
        correctWord = 0;
    }

    public int wordPerMinuteCalculation() {
        if (getTime() == 0.0){
            return 0;
        }
        int wpm = (int) ((keyPress - (keyPress - correctWord)) * 60 / (getTime() * 5));
        return wpm;
    }

    public int accuracyCalculation(Character c) {
        if (c.isCorrect()) {
            correctWord++;
        }
        int acc = (int) (correctWord * 100 / keyPress);
        return acc;
    }

    public double getTime() {
        return stopwatch.getElapsedTime();
    }

    public void setGame() {
        setting.setMode("word");
        setting.setLevel(1);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}