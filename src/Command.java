public class Command {
    private Character character;
    private double time;

    public Command(Character character, double time){
        this.character = character;
        this.time = time;
    }

    public char getCharacter() {
        return character.getaChar();
    }


    public double getTime() {
        return time;
    }

    public void execute(Character getChar){
        getChar.setType(character.isType());
        getChar.setCorrect(character.isCorrect());
        getChar.setSinceWrong(character.isSinceWrong());
    }
}