public class Character {
    private char aChar;
    private boolean isType;
    private boolean isCorrect;

    public Character(char aChar){
        this.aChar = aChar;
        this.isType = false;
        this.isCorrect = false;
    }

    public char getaChar() {
        return aChar;
    }

    public boolean isType() {
        return isType;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setType(boolean type) {
        isType = type;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}