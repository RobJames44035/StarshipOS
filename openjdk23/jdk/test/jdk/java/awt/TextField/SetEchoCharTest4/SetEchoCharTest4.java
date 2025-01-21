/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

/*
 * @test
 * @bug 4226580
 * @summary TextField with echoChar add+remove+add seems to be broken
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual SetEchoCharTest4
 */

public class SetEchoCharTest4 extends Frame implements ActionListener {
    TextField tf1 = new TextField(8);
    TextField tf2 = new TextField(8);
    TextField tf3 = new TextField(8);
    Button b = new Button("Click Several Times");

    static final String INSTRUCTIONS = """
                    Type in the first text field and * characters will echo.
                    Type in the second text field and $ characters will echo.
                    Type in the third text field and # characters will echo.

                    Hit the button several times.  All characters should remain
                    the same and the test should not crash.

                    Make sure the actual text matches what you typed in for each field.
                    Press Pass if everything's ok, otherwise Fail
           """;

    public SetEchoCharTest4() {
        setLayout(new FlowLayout());
        tf1.setEchoChar('*');
        tf2.setEchoChar('$');
        tf3.setEchoChar('#');
        addStuff();
        b.addActionListener(this);
        setSize (200,200);
    }

    private void addStuff() {
        add(tf1);
        add(tf2);
        add(tf3);
        add(b);
    }

    public void actionPerformed(ActionEvent ae) {
        PassFailJFrame.log("TextField1 = " + tf1.getText());
        PassFailJFrame.log("TextField2 = " + tf2.getText());
        PassFailJFrame.log("TextField3 = " + tf3.getText());
        PassFailJFrame.log("--------------");
        setVisible(false);
        remove(tf1);
        remove(tf2);
        remove(tf3);
        remove(b);
        addStuff();
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PassFailJFrame.builder()
                .title("Set Echo Character Test")
                .testUI(SetEchoCharTest4::new)
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(40)
                .logArea()
                .build()
                .awaitAndCheck();
    }
}
