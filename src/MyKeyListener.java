import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class MyKeyListener implements KeyListener {
    JTextField textField;
    public String gametext = " HE standard12345";

    public MyKeyListener(JTextField textField) {
        this.textField = textField;
    }

    public void keyPressed(KeyEvent e) {
        // called when a key is pressed down
        int keyCode = e.getKeyCode();
        System.out.println("Key Pressed: " + KeyEvent.getKeyText(keyCode));
    }

    public void keyTyped(KeyEvent e) {
        // called when a key is typed
        char keyChar = e.getKeyChar();
        if (keyChar == gametext.charAt(0)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

    public void keyReleased(KeyEvent e) {
        // called when a key is released
        int keyCode = e.getKeyCode();
        System.out.println("Key Released: " + KeyEvent.getKeyText(keyCode));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("KeyListener Example");
//        JLabel label = new JLabel(
//                "<html>" +
//                        "<font size='5' color=blue> Welcome to</font> " +
//                        "<font size='6' color=red> Tutorials Point</font>" +
//                        "<font size='6' color=green> Tutorials Point</font>" +
//                        "<font size='6' color=green> Tutorials Point</font>" +
//                        "<font size='6' color=green> Tutorials Point</font>" +
//                        "<font size='6' color=green> Tutorials Point</font>" +
//                        "</html>"
//        );
        JTextField textField = new JTextField(20);
        textField.addKeyListener(new MyKeyListener(textField));
        frame.getContentPane().add(textField);
//        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
}