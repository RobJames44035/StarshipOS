/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8169355
 * @summary Check if Spanish diacritical signs could be typed for TextField
 * @requires (os.family == "windows")
 * @library /java/awt/regtesthelpers
 * @run main/manual SpanishDiacriticsTest
*/


import java.util.concurrent.locks.LockSupport;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class SpanishDiacriticsTest {

    static final String INSTRUCTIONS = """
      This test requires the following keyboard layout to be installed:
      Windows OS: Spanish (United States) with 'Latin American' keyboard layout.
      If using a US layout, the results should still be as described but
      you have not tested the real bug.

       1. A frame with a text field should be displayed.
       2. Set focus to the text field and switch to Spanish
          with 'Latin American' keyboard layout.
       3. Type the following: ' ' o - i.e. single quote two times, then o.
          If your keyboard has a US physical layout the [ key can be used
          to type the single quote when in 'Latin American' keyboard mode.
       4. Type these characters at a normal speed but do NOT be concerned
          that they take several seconds to display. That is an
          expected behaviour for this test.

       If the text field displays the same three characters you typed: ''o
       (i.e. two single quotes followed by o without an acute)
       then press Pass; otherwise press Fail.
       """;

    public static void main(String[] args) throws Exception {

        PassFailJFrame.builder()
                      .title("Spanish Diacritics")
                      .instructions(INSTRUCTIONS)
                      .rows(20)
                      .columns(50)
                      .testUI(SpanishDiacriticsTest::createTestUI)
                      .build()
                      .awaitAndCheck();
    }

    static JFrame createTestUI() {
        JFrame frame = new JFrame("Spanish Diacritics Test Frame");
        JTextField textField = new JTextField(20);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                LockSupport.parkNanos(1_000_000_000L);
            }
        });
        frame.add(textField);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        return frame;
    }
}

