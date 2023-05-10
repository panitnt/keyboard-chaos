import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable {
    private WordController wordController;
    private Setting setting;
    private Thread thread;
    private List<WordController> word_generate= new ArrayList<WordController>();

    public Game(){
        setting = Setting.getInstance();
        initWordController();
    }
        public void initWordController(){
        word_generate.add(new WordController("hello"));
        word_generate.add(new WordController(" "));
        word_generate.add(new WordController("world"));
    }
    public void start(){
        thread = new Thread(){
            @Override
            public void run(){

            }
        };
    };
}
