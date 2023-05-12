public class Character {
    private char aChar;
    private boolean isType;
    private boolean isCorrect;
    private boolean sinceWrong;
    private int row;
    private int col;

    public Character(char aChar, int r, int c){
        this.aChar = aChar;
        this.isType = false;
        this.isCorrect = false;
        this.sinceWrong = false;
        this.row = r;
        this.col = c;
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

    public boolean isSinceWrong() {
        return sinceWrong;
    }

    public void setSinceWrong(boolean sinceWrong) {
        this.sinceWrong = sinceWrong;
    }

    public void setType(boolean type) {
        isType = type;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getX() {
        return row;
    }

    public int getY() {
        return col;
    }
}