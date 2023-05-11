import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;

public class GameUI extends JFrame implements Observer {
    private GamePanel gamePanel;
    private WordPanel wordPanel;
    private Game game;

//    private Replay replay;
    public GameUI() {
        gamePanel = new GamePanel();
        wordPanel = new WordPanel();
        game = new Game();
        game.start();

        String second = Double.toString(game.getTime());
        JLabel timeLabel = new JLabel(second);
        timeLabel.setBounds(400,50, 100, 20);
        timeLabel.setFont(new Font("Monospaced", Font.BOLD, 20));


        add(timeLabel);
        this.updateTime(timeLabel);

//        replay = new Replay();
//        gamePanel.add(wordPanel);
        JButton replayButton = new JButton("Replay");
        replayButton.setBounds(400,100,95,30);
        add(replayButton);

        add(gamePanel);
        add(wordPanel);
        pack();
        setLocationRelativeTo(null);
        setSize(900, 600);
        setVisible(true);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg) {
        wordPanel.repaint();
        gamePanel.repaint();
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
//        private final int PADDING = 50;

        public GamePanel() {
            setBackground(Color.LIGHT_GRAY);
//            setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
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
        @Override
        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    class WordPanel extends JPanel {
        private final int FONT_SIZE = 30;

        public WordPanel() {
            setPreferredSize(new Dimension(getWidth() * FONT_SIZE, FONT_SIZE));
            setBackground(Color.pink);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
//            Command h = new Command('H');
//            h.setCorrect(true);
//            paintWord(g, h, 1, 1);
//            Command e = new Command('E');
//            e.setQueue(true);
//            paintWord(g, e, 2, 1);
//            paintWord(g, new Command('L'), 3, 1);
//            paintWord(g, new Command('L'), 4, 1);
//            paintWord(g, new Command('O'), 5, 1);
//            paintWord(g, new Command(' '), 6, 1);
//            paintWord(g, new Command('W'), 7, 1);
//            paintWord(g, new Command('O'), 8, 1);
//            paintWord(g, new Command('R'), 9, 1);
//            paintWord(g, new Command('L'), 10, 1);
//            paintWord(g, new Command('D'), 11, 1);


            for(int row = 0; row < 45; row++){
                for(int col = 0; col <20; col++){
                    paintWord(g, new Character('H'), row, col);
//                    paintWord(g, new Command('E'), row, col);
//                    paintWord(g, new Command('L'), row, col);
//                    paintWord(g, new Command('L'), row, col);
                }
            }

        }

        private void paintWord(Graphics g, Character c, int x, int y) {
            g.setColor(Color.WHITE);
            g.fillRect(x * (this.FONT_SIZE-10), y * this.FONT_SIZE, this.FONT_SIZE-10, this.FONT_SIZE);
            if (c.isCorrect()) {
                g.setColor(Color.BLACK);
            } else if (c.isType() && (!c.isCorrect())) {
                g.setColor(Color.RED);
            } else {

                g.setColor(Color.LIGHT_GRAY);
            }
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));
            g.drawString(c.getaChar() + "", (x * (this.FONT_SIZE-10)) + 1, (y * this.FONT_SIZE) + 26);

        }
    }

    public static void main(String[] args) {
        GameUI game = new GameUI();
    }
}