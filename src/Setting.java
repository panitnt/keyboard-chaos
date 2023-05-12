public class Setting {
    private int mode = 0;
    private int level = 0;
    private static Setting instance;

    public static Setting getInstance() {
        if (instance == null) {
            return new Setting();
        }
        return instance;
    }

    private Setting() {
        this.mode = mode;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public int getMode() {
        return mode;
    }

    public void setLevel(int level) {
        if ((level < 4) && (level > 0)) {
            this.level = level;
        }
    }

    public void setMode(int mode) {
        if ((mode == 0)||(mode == 15) || (mode==30)){
            this.mode = mode;
        }
    }
}
