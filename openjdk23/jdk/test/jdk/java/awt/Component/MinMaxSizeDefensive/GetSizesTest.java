/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
  @test
  @bug 4783989
  @summary  get(Preferred|Minimum|Maximum)Size() must not return a reference.
  The object copy of Dimension class needed.
  @key headful
  @run main GetSizesTest
*/

import java.awt.Button;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;

public class GetSizesTest extends Frame {
    Button b;

    public static void main(final String[] args) throws InterruptedException,
            InvocationTargetException {
        GetSizesTest app = new GetSizesTest();
        EventQueue.invokeAndWait(() -> {
            try {
                app.init();
                app.start();
            } finally {
                app.dispose();
            }
        });
    }

    public void init() {
        b = new Button("button");
        add(b);
    }

    public void start () {
        setSize(200, 200);
        setLocationRelativeTo(null);
        setVisible(true);
        validate();

        System.out.println("Test set for Container (Frame).");

        Dimension dimPref = getPreferredSize();
        dimPref.setSize(101, 101);
        if (getPreferredSize().equals(new Dimension(101, 101))) {
            throw new RuntimeException("Test Failed for: " + dimPref);
        }
        System.out.println("getPreferredSize() Passed.");

        Dimension dimMin = getMinimumSize();
        dimMin.setSize(101, 101);
        if (getMinimumSize().equals(new Dimension(101, 101))) {
            throw new RuntimeException("Test Failed for: " + dimMin);
        }
        System.out.println("getMinimumSize() Passed.");

        Dimension dimMax = getMaximumSize();
        dimMax.setSize(101, 101);
        if (getMaximumSize().equals(new Dimension(101, 101))) {
            throw new RuntimeException("Test Failed for: " + dimMax);
        }
        System.out.println("getMaximumSize() Passed.");

        System.out.println("Test set for Component (Button).");

        dimPref = b.getPreferredSize();
        dimPref.setSize(33, 33);
        if (b.getPreferredSize().equals(new Dimension(33, 33))) {
            throw new RuntimeException("Test Failed for: " + dimPref);
        }
        System.out.println("getPreferredSize() Passed.");

        dimMin = b.getMinimumSize();
        dimMin.setSize(33, 33);
        if (b.getMinimumSize().equals(new Dimension(33, 33))) {
            throw new RuntimeException("Test Failed for: " + dimMin);
        }
        System.out.println("getMinimumSize() Passed.");

        dimMax = b.getMaximumSize();
        dimMax.setSize(33, 33);
        if (b.getMaximumSize().equals(new Dimension(33, 33))) {
            throw new RuntimeException("Test Failed for: " + dimMax);
        }
        System.out.println("getMaximumSize() Passed.");
        System.out.println("GetSizesTest Succeeded.");
    }
}
