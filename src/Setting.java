public class Setting {
    private String mode;
    private int level;
    private static Setting instance;
    public static Setting getInstance(){
        if (instance == null) {
            return new Setting();
        }
        return instance;
    }

    private Setting(){
        this.mode = mode;
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public String getMode() {
        return mode;
    }

    public void setLevel(int level) {
        if ((level<4) && (level >0)){
            this.level = level;
        }
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
