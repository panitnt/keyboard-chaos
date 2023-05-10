public class Setting {
    private int level;
    private String mode;
    private static Setting instance;
    public static Setting getInstance(){
        if (instance == null) {
            return new Setting();
        }
        return instance;
    }

    private Setting(){
    }
    public int getLevel() {
        return level;
    }

    public String getMode() {
        return mode;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
