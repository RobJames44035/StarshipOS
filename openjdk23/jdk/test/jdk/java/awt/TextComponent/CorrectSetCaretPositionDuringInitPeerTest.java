/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 5100200
  @summary IAE in X11 text field peer code
  @key headful
*/

import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.TextField;

public class CorrectSetCaretPositionDuringInitPeerTest
{
    static TextField tf;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        try{
            EventQueue.invokeAndWait(() -> {
                frame = new Frame("Caret Position test");
                tf = new TextField("Very very very long string");
                tf.setSelectionStart(10);
                tf.setText("Short"); // now TextField.length() less than 10
                frame.add(tf);

                frame.pack();
                frame.setVisible(true);
            });
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
