import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class TypingUI extends JFrame implements Observer {
    private WordPanel wordPanel;
    private Gui gui;
    private Game game;
    private Replay replay;
    private Stopwatch stepWatch;

    public TypingUI() {
        super();
        stepWatch = new Stopwatch();
        replay = new Replay();

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
    public void update(Observable o, Object arg) {
        wordPanel.repaint();
        gui.updateTime(game.getTime());
        int wpm = game.wordPerMinute;
        gui.wpm.setText("WPM: " + wpm);
    }

    class WordPanel extends JPanel {
        private final int FONT_SIZE = 30;

        public WordPanel() {
            setPreferredSize(new Dimension(getWidth() * FONT_SIZE, FONT_SIZE));
            setBackground(Color.LIGHT_GRAY);
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int row = 1;
            int col = 1;
            for (Character w : game.word_generate) {
                paintWord(g, w, row, col);
                row++;
                if (row > 43) {
                    col++;
                    row = 1;
                }
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

    class Gui extends JPanel {
        private JLabel timeLabel;
        private JLabel wpm;
        private JLabel accu;
        private JButton replayButton;

        private JButton restartButton;

        public Gui() {
            setLayout(new FlowLayout());
            timeLabel = new JLabel("Time: 0");
            timeLabel.setBounds(400, 50, 100, 20);
            timeLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(timeLabel);

            replayButton = new JButton("Replay");
            replayButton.setEnabled(false);
            replayButton.addActionListener(e -> {
                replayButton.setEnabled(false);
            });
            replayButton.addActionListener(e -> {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        synchronized (TypingUI.this) {
                            List<Command> commands = replay.getCommands();
                            // do here
                            game.resets();
                            game.index = 0;
                            Command currentCommand = null;
                            Command nextCommand = null;
                            for (Command command : commands) {

                                nextCommand = command;
                                if(currentCommand == null){
                                    currentCommand = command;
                                    continue;
                                }

                                Character getChar = game.word_generate.get(game.index);
                                currentCommand.execute(getChar);
                                try {
                                    double time = nextCommand.getTime() > 0? nextCommand.getTime() : 1;
                                    TypingUI.this.wait((long) time);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                System.out.println(currentCommand.getCharacter());
                                System.out.println(currentCommand.getTime());
                                game.index++;
                                wordPanel.repaint();
                                currentCommand = nextCommand;
                            }
                            Character getChar = game.word_generate.get(game.index);
                            commands.get(commands.size() - 1).execute(getChar);
                            System.out.println(currentCommand.getCharacter());
                            System.out.println(currentCommand.getTime());
                            wordPanel.repaint();

                            replayButton.setEnabled(true);
                            TypingUI.this.notifyAll();
                        }
                    }
                };
                thread.start();

            });
            add(replayButton);

            restartButton = new JButton("Restart");
            restartButton.setEnabled(false);
            restartButton.addActionListener(e -> {
                restartButton.setEnabled(false);
                replayButton.setEnabled(false);
                replay.reset();
                accu.setText("");
                wpm.setText("");
                game.resets();
                TypingUI.this.requestFocus();
            });
            add(restartButton);

            accu = new JLabel("");
            accu.setBounds(400, 50, 100, 20);
            accu.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(accu);

            wpm = new JLabel("");
            wpm.setBounds(400, 50, 100, 20);
            wpm.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(wpm);
        }

        public void updateTime(Double time) {
            timeLabel.setText("Time: " + time);
        }
    }

    class Controller extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPress++;
            if (!game.isPlaying() && (game.index == 0)) {
                game.start();
                stepWatch.start();
                TypingUI.this.requestFocus();
            }
            if (game.index >= game.word_generate.size()) {
                game.stops();
                System.out.println("Game end!");
            } else {
                Character getChar = game.word_generate.get(game.index);
                if (e.getKeyCode() != 16) {
//                    System.out.println(getChar.getaChar() + " " + e.getKeyChar() + " " + e.getKeyCode());
                    if (e.getKeyChar() == getChar.getaChar()) {
                        getChar.setType(true);
                        getChar.setCorrect(true);
                        game.index++;

                        Command command = new Command(getChar, stepWatch.getElapsedTimeMilliSecond());
                        stepWatch.start();
                        replay.addCommand(command);

                        if (game.index == game.word_generate.size()) {
                            game.stops();
                            game.setPlaying(false);
                            gui.replayButton.setEnabled(true);
                            gui.restartButton.setEnabled(true);
                        }
                    } else {
                        getChar.setType(true);
                        getChar.setSinceWrong(true);
                    }
                    int accu = game.accuracyCalculation(getChar);
                    gui.accu.setText("Accuracy: " + accu);
                    int wpm = game.wordPerMinuteCalculation();
                    gui.wpm.setText("WPM: " + wpm);

                }
            }
        }
    }

    public static void main(String[] args) {
        TypingUI typingUI = new TypingUI();
        typingUI.setVisible(true);
    }
}
