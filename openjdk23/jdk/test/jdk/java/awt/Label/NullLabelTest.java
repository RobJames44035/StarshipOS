/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 6215905
  @summary Tests that passing null value to Label.setText(String) doesn't
            lead to VM crash.
  @key headful
  @run main NullLabelTest
*/

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Label;
import java.awt.Frame;

public class NullLabelTest {

    static Frame frame;
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            try {
                frame = new Frame();
                Label l = new Label("A");
                frame.add(l);
                frame.setLayout(new BorderLayout());
                frame.setSize(200, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                l.setText(null);
            } finally {
                if (frame != null) {
                    frame.dispose();
                }
            }
        });
    }
}
