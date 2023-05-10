public class Command {
    private final char character;
    private boolean isCorrect;
    private boolean queue;
    private float time;
    public Command(char character){
        this.character = character;
        this.isCorrect = false;
        this.queue = false;
    }

    public char getCharacter() {
        return character;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isQueue() {
        return queue;
    }

    public void setQueue(boolean queue) {
        this.queue = queue;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

}
