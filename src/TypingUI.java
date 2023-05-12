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
    public int accuCal;
    public int wpmCal;

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
        setSize(900, 700);
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
        if (!game.isPlaying()){
            gui.restartButton.setEnabled(true);
            gui.replayButton.setEnabled(true);
        }
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
            for (Character w : game.word_generate) {
                paintWord(g, w);
            }
        }

        private void paintWord(Graphics g, Character c) {
            g.setColor(Color.WHITE);
            g.fillRect(c.getX() * (this.FONT_SIZE - 10), c.getY() * this.FONT_SIZE, this.FONT_SIZE - 10, this.FONT_SIZE);
            if (c.isCorrect() && (!c.isSinceWrong())) {
                g.setColor(Color.BLACK);
            } else if (c.isCorrect() && c.isSinceWrong()) {
                g.setColor(Color.RED);
            } else if (c.isType() && (!c.isCorrect())) {
                g.setColor(Color.PINK);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.setFont(new Font("Monospaced", Font.PLAIN, 28));
            g.drawString(c.getaChar() + "", (c.getX() * (this.FONT_SIZE - 10)) + 1, (c.getY() * this.FONT_SIZE) + 23);
        }
    }

    class Gui extends JPanel {
        private JLabel timeLabel;
        private JLabel wpm;
        private JLabel accu;
        private JLabel noteLabel;
        private JButton replayButton;

        private JButton restartButton;
        private JButton easyButton;
        private JButton mediumButton;
        private JButton hardButton;
        private JButton normalButton;
        private JButton fifteenButton;
        private JButton thirtyButton;


        public Gui() {
            setPreferredSize(new Dimension(900, 100));
            setLayout(new GridLayout(4, 2));

            timeLabel = new JLabel("Time: 0");
            timeLabel.setBounds(400, 50, 100, 20);
            timeLabel.setFont(new Font("Monospaced", Font.BOLD, 20));

            add(timeLabel);
            accu = new JLabel("");
            accu.setBounds(400, 50, 100, 20);
            accu.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(accu);

            wpm = new JLabel("");
            wpm.setBounds(400, 50, 100, 20);
            wpm.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(wpm);

            noteLabel = new JLabel("");
            noteLabel.setBounds(400, 50, 100, 20);
            noteLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
            add(noteLabel);

            replayButton = new JButton("Replay");
            replayButton.setEnabled(false);
            replayButton.addActionListener(e -> {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        replayButton.setEnabled(false);
                        restartButton.setEnabled(false);
                        synchronized (TypingUI.this) {
                            List<Command> commands = replay.getCommands();
                            // do here
                            game.index = 0;
                            game.resetTyped();
                            wordPanel.repaint();

                            Command currentCommand = null;
                            Command nextCommand = null;
                            for (Command command : commands) {

                                nextCommand = command;
                                if (currentCommand == null) {
                                    currentCommand = command;
                                    continue;
                                }

                                timeLabel.setText("Time: " + String.valueOf(command.getRealTime()));
                                Character getChar = game.word_generate.get(game.index);
                                currentCommand.execute(getChar);
                                try {
                                    double time = nextCommand.getTime() > 0 ? nextCommand.getTime() : 1;
                                    TypingUI.this.wait((long) time);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                wordPanel.repaint();
                                accu.setText("Accuracy: " + String.valueOf(command.getAccuracy()) + "%");
                                wpm.setText("WPM: " + String.valueOf(command.getWpm()));
                                game.index++;
                                currentCommand = nextCommand;
                            }
                            Character getChar = game.word_generate.get(game.index);
                            System.out.println(getChar);
                            commands.get(commands.size() - 1).execute(getChar);
                            accu.setText("Accuracy: " + String.valueOf(accuCal) + "%");
                            wpm.setText("WPM: " + String.valueOf(wpmCal));
                            wordPanel.repaint();
                            replayButton.setEnabled(true);
                            restartButton.setEnabled(true);
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
                easyButton.setEnabled(true);
                mediumButton.setEnabled(true);
                hardButton.setEnabled(true);
                normalButton.setEnabled(true);
                fifteenButton.setEnabled(true);
                thirtyButton.setEnabled(true);
                replay.reset();
                timeLabel.setText("Time: ");
                game.resets();
                accu.setText("");
                wpm.setText("");
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(restartButton);

            easyButton = new JButton("Easy");
            easyButton.addActionListener(e -> {
                game.setLevel(1);
                game.initWordController(1);
                easyButton.setEnabled(false);
                mediumButton.setEnabled(true);
                hardButton.setEnabled(true);
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(easyButton);

            mediumButton = new JButton("Medium");
            mediumButton.addActionListener(e -> {
                game.setLevel(2);
                game.initWordController(2);
                easyButton.setEnabled(true);
                mediumButton.setEnabled(false);
                hardButton.setEnabled(true);
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(mediumButton);

            hardButton = new JButton("Hard");
            hardButton.addActionListener(e -> {
                game.setLevel(3);
                game.initWordController(3);
                easyButton.setEnabled(true);
                mediumButton.setEnabled(true);
                hardButton.setEnabled(false);
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(hardButton);

            normalButton = new JButton("Normal");
            normalButton.addActionListener(e -> {
                game.setMode(0);
                normalButton.setEnabled(false);
                fifteenButton.setEnabled(true);
                thirtyButton.setEnabled(true);
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(normalButton);

            fifteenButton = new JButton("15 sec");
            fifteenButton.addActionListener(e -> {
                game.setMode(15);
                normalButton.setEnabled(true);
                fifteenButton.setEnabled(false);
                thirtyButton.setEnabled(true);
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(fifteenButton);

            thirtyButton = new JButton("30 sec");
            thirtyButton.addActionListener(e -> {
                game.setMode(30);
                normalButton.setEnabled(true);
                fifteenButton.setEnabled(true);
                thirtyButton.setEnabled(false);
                wordPanel.repaint();
                TypingUI.this.requestFocus();
            });
            add(thirtyButton);
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
                gui.easyButton.setEnabled(false);
                gui.mediumButton.setEnabled(false);
                gui.hardButton.setEnabled(false);
                gui.normalButton.setEnabled(false);
                gui.fifteenButton.setEnabled(false);
                gui.thirtyButton.setEnabled(false);
                gui.noteLabel.setText(((game.getLevel() == 1) ? "Easy" : (game.getLevel() == 2) ? "Medium" : "Hard") + " " + game.getMode());
                TypingUI.this.requestFocus();
            }
            if (game.index >= game.word_generate.size()) {
                game.stops();
                System.out.println("Game end!");
            }
//            else if ((game.getMode() != 0) && ((int) game.getTime() >= game.getMode()-1)) {
////                game.stops();
//                System.out.println("Game end!");
//                gui.restartButton.setEnabled(true);
//                gui.replayButton.setEnabled(true);
//            }
            else {
                if (e.getKeyCode() != 16) {
                    Character getChar = game.word_generate.get(game.index);
                    Character charCommand = new Character(getChar.getaChar(), getChar.getRow(), getChar.getCol());
                    if (e.getKeyChar() == getChar.getaChar()) {
                        getChar.setType(true);
                        getChar.setCorrect(true);
                        game.index++;

                        charCommand.setType(getChar.isType());
                        charCommand.setCorrect(getChar.isCorrect());
                        charCommand.setSinceWrong(getChar.isSinceWrong());
                        replay.addCommand(new Command(charCommand, stepWatch.getElapsedTimeMilliSecond(), game.stopwatch.getElapsedTime(), accuCal, wpmCal));
                        stepWatch.start();

                        if (game.index == game.word_generate.size()) {
                            game.stops();
                            gui.replayButton.setEnabled(true);
                            gui.restartButton.setEnabled(true);
                        }
                    } else {
                        getChar.setType(true);
                        getChar.setSinceWrong(true);
                    }
                    accuCal = game.accuracyCalculation(getChar);
                    wpmCal = game.wordPerMinuteCalculation();
                    gui.accu.setText("Accuracy: " + accuCal + "%");
                    gui.wpm.setText("WPM: " + wpmCal);

                }
            }
        }
    }

    public static void main(String[] args) {
        TypingUI typingUI = new TypingUI();
        typingUI.setVisible(true);
    }
}
