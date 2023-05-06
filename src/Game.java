public class Game {
    private Word word;
    private Setting setting;

    public Game(){
        word = new Word();
        setting = Setting.getInstance();
    }
    public void start(){
    };
}
