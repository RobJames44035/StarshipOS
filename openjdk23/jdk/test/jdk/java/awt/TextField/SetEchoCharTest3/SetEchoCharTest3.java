/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4222122
 * @summary TextField.setEchoCharacter() seems to be broken
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual SetEchoCharTest3
 */

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.lang.reflect.InvocationTargetException;

public class SetEchoCharTest3 extends Frame {
    static String INSTRUCTIONS = """
             Type in the text field and "*" characters should echo.
             If only one "*" echoes and then the system beeps after
             the second character is typed, then press Fail, otherwise press Pass.
             """;
    public SetEchoCharTest3() {
        setLayout(new FlowLayout());
        add(new Label("Enter text:"));
        TextField tf = new TextField(15);
        tf.setEchoChar('*');
        add(tf);
        pack();
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PassFailJFrame.builder()
                .title("Set Echo Char Test 3")
                .testUI(SetEchoCharTest3::new)
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 1)
                .columns(40)
                .build()
                .awaitAndCheck();
    }
}
