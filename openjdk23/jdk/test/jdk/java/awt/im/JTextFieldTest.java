/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4226191
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary  Verify that Lightweight text components (like swing JTextField)
 *           work correctly with IM when there is an uneditable peered
 *           TextField/TextArea in the same parent Frame
 * @run main/manual JTextFieldTest
 */

import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class JTextFieldTest {
    private static final String INSTRUCTIONS =
            """
             Please run this test in a CJK (Chinese/Japanese/Korean) locale
             with input method support. If you could add input in the swing
             JTextField, then the test has passed!
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame
            .builder()
            .title("JTextFieldTest")
            .instructions(INSTRUCTIONS)
            .rows(5)
            .columns(40)
            .testUI(JTextFieldTest::createAndShowGUI)
            .build()
            .awaitAndCheck();
    }

    public static JFrame createAndShowGUI() {
        JFrame frame = new JFrame("Test Frame");
        frame.setLayout(new FlowLayout());
        TextField tf1 = new TextField("ABCDEFGH", 10);
        tf1.setEditable(false);
        JTextField tf2 = new JTextField("12345678", 10);
        frame.getContentPane().add(tf1);
        frame.getContentPane().add(tf2);
        frame.pack();
        return frame;
    }
}
