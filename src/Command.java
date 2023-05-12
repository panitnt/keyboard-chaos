public class Command {
    private Character character;
    private double time;
    private double realTime;
    private double accuracy;
    private double wpm;


    public Command(Character character, double time, double realTime, double accuracy, double wpm){
        this.character = character;
        this.time = time;
        this.realTime = realTime;
        this.accuracy = accuracy;
        this.wpm = wpm;
    }

    public char getCharacter() {
        return character.getaChar();
    }

    public double getTime() {
        return time;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getWpm() {
        return wpm;
    }


    public double getRealTime() { return realTime; }
    public void execute(Character getChar){
        getChar.setType(character.isType());
        getChar.setCorrect(character.isCorrect());
        getChar.setSinceWrong(character.isSinceWrong());
    }
}