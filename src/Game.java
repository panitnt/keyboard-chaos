import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Game extends JFrame {
    private GamePanel gamePanel = new GamePanel();

    public Game() {
        add(gamePanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    class GamePanel extends JPanel {
        public GamePanel() {
            setPreferredSize(new Dimension(1600, 800));
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
    }
}