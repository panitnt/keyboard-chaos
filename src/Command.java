public class Command {
    private char character;
    private boolean isCorrect;
    private float time;

    public Command(char character, boolean isCorrect, float time){
        this.character = character;
        this.isCorrect = isCorrect;
        this.time = time;
    }

    public char getCharacter() {
        return character;
    }

    public boolean getIsCorrect(){
        return isCorrect;
    }

    public float getTime() {
        return time;
    }
}
