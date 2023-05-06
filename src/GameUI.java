import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameUI extends JFrame {
    private GamePanel gamePanel;
    private WordPanel wordPanel;
    public GameUI() {
        gamePanel = new GamePanel();
        wordPanel = new WordPanel();

        gamePanel.add(wordPanel);
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    class GamePanel extends JPanel {
        private final int PADDING = 250;

        public GamePanel() {
            setBackground(Color.GRAY);
            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        }
    }

    class WordPanel extends JPanel{
        private final int FONT_SIZE = 20;
        public WordPanel(){
            setPreferredSize(new Dimension(getWidth()*FONT_SIZE, FONT_SIZE));
            setBackground(Color.pink);
        }
    }

    public static void main(String[] args) {
        GameUI game = new GameUI();
    }
}