import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class TypingUI extends JFrame implements Observer {
    private WordPanel wordPanel;
    private Gui gui;
    private Game game;

    public TypingUI() {
        super();
        addKeyListener(new Controller());
        setLayout(new BorderLayout());

        wordPanel = new WordPanel();
        add(wordPanel, BorderLayout.CENTER);

        gui = new Gui();
        add(gui, BorderLayout.SOUTH);
        game = new Game();
        game.addObserver(this);
//        game.start();

//        JButton replayButton = new JButton("Replay");
//        replayButton.setBounds(400, 100, 95, 30);
//        add(replayButton, BorderLayout.SOUTH);
//        add(timeLabel, BorderLayout.SOUTH);
//        pack();
//        setLocationRelativeTo(null);
        setSize(900, 600);
        setVisible(true);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void update(Observable o, Object arg){
        wordPanel.repaint();
        gui.updateTime(game.getTime());
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
            int row = 1;
            for (Character w : game.word_generate) {
                paintWord(g, w, row, 1);
                row++;
            }
        }

        private void paintWord(Graphics g, Character c, int x, int y) {
            g.setColor(Color.WHITE);
            g.fillRect(x * (this.FONT_SIZE - 10), y * this.FONT_SIZE, this.FONT_SIZE - 10, this.FONT_SIZE);
            if (c.isCorrect() && (!c.isSinceWrong())) {
                g.setColor(Color.BLACK);
            } else if (c.isCorrect() && c.isSinceWrong()) {
                g.setColor(Color.RED);
            } else if (c.isType() && (!c.isCorrect())) {
                g.setColor(Color.LIGHT_GRAY);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.setFont(new Font("Monospaced", Font.PLAIN, 30));
            g.drawString(c.getaChar() + "", (x * (this.FONT_SIZE - 10)) + 1, (y * this.FONT_SIZE) + 26);

        }
    }
    class Gui extends JPanel{
        private JLabel timeLabel;
        private JButton replayButton;
        public Gui(){
            setLayout(new FlowLayout());
            timeLabel = new JLabel("Time: 0");
            timeLabel.setBounds(400, 50, 100, 20);
            timeLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(timeLabel);


//            replayButton = new JButton("Replay");
//            replayButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
////                    game.start();
//                    replayButton.setEnabled(false);
//                    TypingUI.this.requestFocus();
//                }
//            });
////            replayButton.setEnabled(false);
//            add(replayButton);
        }
        public void updateTime(Double time){
            timeLabel.setText("Time: " + time);
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!game.isPlaying()) {
                game.start();
                TypingUI.this.requestFocus();
                game.setPlaying(true);
            }

            Character getChar = game.word_generate.get(game.index);
            if (e.getKeyCode() != 16) {
                System.out.println(getChar.getaChar() + " " + e.getKeyChar() + " " + e.getKeyCode());
                if (e.getKeyChar() == getChar.getaChar()) {
//                    System.out.println("True");
                    getChar.setType(true);
                    getChar.setCorrect(true);
                    game.index++;
                } else {
                    getChar.setType(true);
                    getChar.setSinceWrong(true);
                }
//                wordPanel.repaint();
//                gui.updateTime(game.stopwatch.getElapsedTime());
            }
        }
    }

    public static void main(String[] args) {
        TypingUI typingUI = new TypingUI();
        typingUI.setVisible(true);
    }
}
