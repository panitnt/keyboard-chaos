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
        stopwatch = Stopwatch.getInstance();
        initWordController(setting.getLevel());
    }

    public void initWordController(int level) {
//        if (level == 1){
//            for (int a = 0; a< 50; a++){
//                List<java.lang.Character> ch = WordEasy.allWord.get(rand.nextInt(0, WordEasy.allWord.size()));
//                for (java.lang.Character c: ch){
//                    word_generate.add(new Character(c));
//                }
//                word_generate.add(new Character(' '));
//            }
//        } else if (level == 2) {
//
//        } else {
//
//        }
//        word_generate.add(new Character('A'));
        int rol = 1;
        int col = 1;
        for (int a = 0; a < 10; a++) {
            List<java.lang.Character> ch = WordEasy.allWord.get(rand.nextInt(0, WordEasy.allWord.size()));
            if (44 - rol < ch.size()) {
                rol = 1;
                col++;
            }
            for (java.lang.Character c : ch) {
                Character newCH = new Character(c, rol, col);
                word_generate.add(newCH);
                rol++;
                System.out.println(newCH.getaChar() + " " + newCH.getX()+" " +newCH.getY());
                if (rol >= 44) {
                    col++;
                    rol = 1;
                }
            }
            word_generate.add(new Character(' ', rol, col));
            rol++;
        }
    }

    public void start() {
        // start typing == start game
        isPlaying = true;

        stopwatch.start();
        setting.setMode("easy");
        setting.setLevel(1);
        // start game logic

        thread = new Thread(() -> {
            while (isPlaying) {
                setChanged();
                notifyObservers();
                wordPerMinute = wordPerMinuteCalculation();
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

    public void resetTyped(){
        for(Character character: word_generate){
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