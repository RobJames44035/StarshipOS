/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
  @test
  @bug 4284124
  @summary FlowLayout gives a wrong size if the first component is hidden.
  @key headful
  @run main PreferredLayoutSize
*/

import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;

public class PreferredLayoutSize {
    public void start() {
        Frame f = new Frame("PreferredLayoutSize");
        int[] widths = new int[2];

        try {
            f.setLocationRelativeTo(null);
            Button b1 = new Button("button 1");
            Button b2 = new Button("button 2");
            f.setLayout(new FlowLayout(FlowLayout.LEFT, 50, 5));
            f.add(b1);
            f.add(b2);
            f.pack();
            f.setVisible(true);
            b1.setVisible(false);
            b2.setVisible(true);
            Dimension d1 = f.getPreferredSize();
            Dimension d2 = b2.getPreferredSize();
            widths[0] = d1.width - d2.width;
            b1.setVisible(true);
            b2.setVisible(false);
            d1 = f.getPreferredSize();
            d2 = b1.getPreferredSize();
            widths[1] = d1.width - d2.width;
            f.setVisible(false);
        } finally {
            f.dispose();
        }

        if (widths[0] != widths[1]) {
            throw new RuntimeException("Test FAILED");
        }
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PreferredLayoutSize test = new PreferredLayoutSize();
        EventQueue.invokeAndWait(test::start);
    }
}
