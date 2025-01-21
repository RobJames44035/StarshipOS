/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */
/*
  @test
  @bug 4198994
  @summary getInsets should return Insets object that is safe to modify
  @key headful
  @run main ClobberSharedInsetsObjectTest
*/

/**
 * ClobberSharedInsetsObjectTest.java
 *
 * summary: The bug is that getInsets directly returns Insets object
 * obtained from peer getInsets.  The latter always return the
 * reference to the same object, so modifying this object will affect
 * other code that calls getInsets.  The test checks that it's safe to
 * modify the Insets object returned by getInsets.  If the change to
 * this object is not visible on the next invocation, the bug is
 * considered to be fixed.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Panel;

public class ClobberSharedInsetsObjectTest {
    static Panel p;

    // Impossible inset value to use for the test
    final static int SENTINEL_INSET_VALUE = -10;
    static Frame f;

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            try {
                // Need a peer anyway, so let the bug manifest visuially, even
                // though we can detect it automatically.
                f = new Frame();
                p = new Panel();
                p.setBackground(Color.red);
                f.setLayout (new BorderLayout ());
                f.add(p, "Center");

                Insets insetsBefore = p.getInsets();
                insetsBefore.top = SENTINEL_INSET_VALUE;

                Insets insetsAfter = p.getInsets();
                if (insetsAfter.top == SENTINEL_INSET_VALUE) { // OOPS!
                    throw new Error("4198994: getInsets returns the same object on subsequent invocations");
                }

                f.setSize (200,200);
                f.setLocationRelativeTo(null);
                f.setVisible(true);

                System.out.println("getInsets is ok.  The object it returns is safe to modify.");
            } finally {
                if (f != null) {
                    f.dispose();
                }
            }
        });
    }
}
