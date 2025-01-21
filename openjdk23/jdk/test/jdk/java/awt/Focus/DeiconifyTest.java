/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4380809
 * @summary Focus disappears after deiconifying frame
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual DeiconifyTest
*/

import java.awt.Button;
import java.awt.Frame;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DeiconifyTest {

    private static final String INSTRUCTIONS = """
         1. Activate frame \"Main frame\"
         be sure that button has focus
         2. Minimize frame and then restore it.
         If the button has focus then test passed, else failed""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("DeiconifyTest Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int)INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(DeiconifyTest::createTestUI)
                .logArea()
                .build()
                .awaitAndCheck();
    }

    private static Frame createTestUI()   {
        Frame frame = new Frame("Main frame");
        Button button = new Button("button");
        button.addFocusListener(new FocusListener() {
              public void focusGained(FocusEvent fe) {
                  println("focus gained");
              }
              public void focusLost(FocusEvent fe) {
                  println("focus lost");
              }
          });
        frame.add(button);
        frame.setSize(300, 100);

        return frame;
    }

    static void println(String messageIn) {
        PassFailJFrame.log(messageIn);
    }
}

