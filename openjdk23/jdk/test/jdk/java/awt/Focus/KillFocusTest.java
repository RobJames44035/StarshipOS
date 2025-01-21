/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */
/*
 * @test
 * @bug 4402942
 * @summary After deactivation and activation of frame, focus should be restored correctlty
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual KillFocusTest
*/

import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class KillFocusTest {

    private static final String INSTRUCTIONS = """
         After starting the test you should see \"Test Frame\"
         with the \"Click me\" text field.
         Click on this text field and try to type something in it.
         Make sure that the field receives focus and you can enter text in it.
         Click on any non-java window.
         Click on \"Click me\" text field to return focus to it
         If the caret is in the text field and you are able to type
         in it then press pass else press fail.""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("KillFocusTest Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(KillFocusTest::createTestUI)
                .logArea()
                .build()
                .awaitAndCheck();
    }

    private static Frame createTestUI() {

        Frame frame = new Frame("KillFocusTest Frame");
        TextField textField = new TextField("Click me", 10);
        textField.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                PassFailJFrame.log("Focus gained");
            }
            public void focusLost(FocusEvent fe) {
                PassFailJFrame.log("Focus lost");
            }
        });
        frame.add(textField);
        frame.setSize(200, 100);
        return frame;
    }


}

