public class Board {
    // the class represent table, each cell contain a character object
    private int index;
    private int row;
    private int col;
    private Character[][] characters;

    public Board(int row, int col){
        this.index = 0;
        this.row = row;
        this.col = col;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Character getCharacter() {
        return characters[row][col];
    }

    public void initCharacters(Setting setting){
        // TODO: create list of character object according to sentences
    }

}