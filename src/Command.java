public abstract class Command {
    private Character character;
    private double time;

    public Command(Character character, double time){
        this.character = character;
        this.time = time;
    }

    public Character getCharacter() {
        return character;
    }


    public double getTime() {
        return time;
    }

    public void execute(){
        // press the keyboard
    }
}