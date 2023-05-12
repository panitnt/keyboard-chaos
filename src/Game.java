import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

public class Game extends Observable {
    private Setting setting;
    protected Stopwatch stopwatch;
    private Thread thread;
    public int index = 0;
    protected List<Character> word_generate = new ArrayList<>();
    private boolean isPlaying = false;
    private int correctWord = 0;
    public float keyPress = 0;
    public int wordPerMinute = 0;
    Random rand = new Random();

    public Game() {
        setting = Setting.getInstance();
        stopwatch = new Stopwatch();
        initWordController(setting.getLevel());
    }

    public void initWordController(int level) {
        word_generate.clear();
        if (level == 1) {
            int loop = 100;
            int rol = 1;
            int col = 1;
            for (int a = 0; a < loop; a++) {
                List<java.lang.Character> ch = WordEasy.allWord.get(rand.nextInt(0, WordEasy.allWord.size()));
                if (44 - rol < ch.size()) {
                    rol = 1;
                    col++;
                }
                for (java.lang.Character c : ch) {
                    Character newCH = new Character(c, rol, col);
                    word_generate.add(newCH);
                    rol++;
                    if (rol >= 44) {
                        col++;
                        rol = 1;
                    }
                }
                if (a < loop - 1) {
                    word_generate.add(new Character(' ', rol, col));
                    rol++;
                }
            }
        } else if (level == 2) {
            int loop = 70;
            int rol = 1;
            int col = 1;
            for (int a = 0; a < loop; a++) {
                List<java.lang.Character> ch = WordMedium.allWord.get(rand.nextInt(0, WordMedium.allWord.size()));
                if (44 - rol < ch.size()) {
                    rol = 1;
                    col++;
                }
                for (java.lang.Character c : ch) {
                    Character newCH = new Character(c, rol, col);
                    word_generate.add(newCH);
                    rol++;
                    if (rol >= 44) {
                        col++;
                        rol = 1;
                    }
                }
                if ((a < loop - 1) && (rol != 1)) {
                    word_generate.add(new Character(' ', rol, col));
                    rol++;
                }
            }
        } else {
            int loop = 50;
            int rol = 1;
            int col = 1;
            for (int a = 0; a < loop; a++) {
                List<java.lang.Character> ch = WordHard.allWord.get(rand.nextInt(0, WordHard.allWord.size()));
                if (44 - rol < ch.size()) {
                    rol = 1;
                    col++;
                }
                for (java.lang.Character c : ch) {
                    Character newCH = new Character(c, rol, col);
                    word_generate.add(newCH);
                    rol++;
                    if (rol >= 44) {
                        col++;
                        rol = 1;
                    }
                }
                if (a < loop - 1) {
                    word_generate.add(new Character(' ', rol, col));
                    rol++;
                }
            }
        }
    }

    public void start() {
        // start typing == start game
        isPlaying = true;

        stopwatch.start();
        setting.setMode(setting.getMode());
        setting.setLevel(setting.getLevel());
        // start game logic

        thread = new Thread(() -> {
            while (isPlaying) {
                setChanged();
                notifyObservers();
                wordPerMinute = wordPerMinuteCalculation();
                if ((getMode() != 0) && ((int) getTime() >= getMode())) {
                    stops();
                    setChanged();
                    notifyObservers();
                }
            }
        });
        thread.start();
    }

    public void stops() {
        stopwatch.stop();
        isPlaying = false;
        // stop game logic
    }

    public void resets() {
        stopwatch.reset();
        // reset game logic
        // reset everything word, time, wpm,
        word_generate.clear();
        index = 0;
        initWordController(setting.getLevel());
        keyPress = 0;
        correctWord = 0;
    }

    public void resetTyped() {
        for (Character character : word_generate) {
            character.setType(false);
            character.setCorrect(false);
            character.setSinceWrong(false);
        }
    }

    public int wordPerMinuteCalculation() {
        if (getTime() == 0.0) {
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

    public void setGame(int mode, int level) {
        setting.setMode(mode);
        setting.setLevel(level);
    }

    public int getMode() {
        return setting.getMode();
    }

    public int getLevel() {
        return setting.getLevel();
    }

    public void setLevel(int level) {
        this.setting.setLevel(level);
    }

    public void setMode(int level) {
        this.setting.setMode(level);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}