import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

public class GameUI extends JFrame {
    private GamePanel gamePanel;
    private WordPanel wordPanel;
    private Game game;
//    private Replay replay;
    public GameUI() {
        gamePanel = new GamePanel();
        wordPanel = new WordPanel();
        game = new Game();
        game.starts();

        String second = Double.toString(game.getTime());
        JLabel label = new JLabel(second);
        label.setBounds(50,50, 100,30);
        add(label);
        this.updateTime(label);

//        replay = new Replay();
//        gamePanel.add(wordPanel);
        JButton replayButton = new JButton("Replay");
        replayButton.setBounds(50,100,95,30);
        add(replayButton);

        add(gamePanel);
        pack();
//        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void updateTime(JLabel label) {
        Thread thread = new Thread() {
            public void run(){
                while(true) {
                    String second = Double.toString(game.getTime());
                    label.setText(second);
                }
            }
        };
        thread.start();
    }
    class GamePanel extends JPanel {
        private final int PADDING = 250;

        public GamePanel() {
            setBackground(Color.GRAY);
            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    super.keyPressed(e);
                    char keyChar = e.getKeyChar();
                    System.out.println("Key Typed: " + keyChar);
                }
            });
            setFocusable(true);
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