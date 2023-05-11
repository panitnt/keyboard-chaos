public class Command {
    private Character character;
    private long time;

    public Command(Character character, long time){
        this.character = character;
        this.time = time;
    }

    public Character getCharacter() {
        return character;
    }


    public float getTime() {
        return time;
    }

    public void execute(){
        // press the keyboard
    }
}